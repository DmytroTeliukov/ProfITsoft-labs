package parsers.impl;

import models.common.ReportDataItem;
import parsers.WriterParser;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import static models.constant.ErrorMessageConstant.FILE_ALREADY_EXIST;

/**
 * A writer/parser implementation for generating XML reports based on report data items.
 */
public class XmlReportDataWriterParser implements WriterParser<ReportDataItem> {
    /**
     * The XML output factory for creating XML writers.
     */
    private static final XMLOutputFactory XML_OUTPUT_FACTORY = XMLOutputFactory.newInstance();

    /**
     * The filename pattern for the output XML file.
     */
    private static final String OUTPUT_PATH_FILENAME = "statistic_by_%s.xml";

    @Override
    public void write(List<ReportDataItem> data, String attributeBy) {
        File file = new File(String.format(OUTPUT_PATH_FILENAME, attributeBy));

        try {
            boolean isNotCreated = !file.createNewFile();

            if (isNotCreated) {
                throw new FileAlreadyExistsException(FILE_ALREADY_EXIST);
            }

            XMLStreamWriter xmlStreamWriter = XML_OUTPUT_FACTORY.createXMLStreamWriter(new FileOutputStream(file), "UTF-8");
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeStartElement("statistic");

            data.forEach(item -> {
                try {
                    writeItem(xmlStreamWriter, item);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();


        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Writes a single report data item to the XML stream.
     *
     * @param xmlStreamWriter the XML writer
     * @param reportDataItem  the report data item to write
     * @throws Exception if an error occurs during writing
     */
    private void writeItem(XMLStreamWriter xmlStreamWriter, ReportDataItem reportDataItem) throws Exception {
        xmlStreamWriter.writeStartElement("item");
        writeElement(xmlStreamWriter, "value", reportDataItem.getValue());
        writeElement(xmlStreamWriter, "count", String.valueOf(reportDataItem.getCount()));
        xmlStreamWriter.writeEndElement();
    }

    /**
     * Writes a single XML element with the specified name and value.
     *
     * @param xmlStreamWriter the XML writer
     * @param elementName     the name of the XML element
     * @param value           the value of the XML element
     * @throws Exception if an error occurs during writing
     */
    private void writeElement(XMLStreamWriter xmlStreamWriter, String elementName, String value) throws Exception {
        xmlStreamWriter.writeStartElement(elementName);
        xmlStreamWriter.writeCharacters(value);
        xmlStreamWriter.writeEndElement();
    }
}
