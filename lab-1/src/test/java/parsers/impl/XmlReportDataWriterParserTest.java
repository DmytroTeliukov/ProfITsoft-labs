package parsers.impl;

import lombok.SneakyThrows;
import models.common.ReportDataItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class XmlReportDataWriterParserTest {

    @Test
    @SneakyThrows
    @DisplayName("should write statistic data to file successfully when write data to file")
    public void shouldWriteStatisticDataToFileSuccessfully_whenWriteDataToWrite() {
        // Given
        XMLStreamWriter xmlStreamWriter = mock(XMLStreamWriter.class);
        doNothing().when(xmlStreamWriter).writeStartDocument(any(), any());
        doNothing().when(xmlStreamWriter).writeStartElement(any());
        doNothing().when(xmlStreamWriter).writeEndElement();
        doNothing().when(xmlStreamWriter).writeCharacters(any());

        XMLOutputFactory xmlOutputFactory = mock(XMLOutputFactory.class);
        when(xmlOutputFactory.createXMLStreamWriter(any(), any())).thenReturn(xmlStreamWriter);

        XmlReportDataWriterParser parser = new XmlReportDataWriterParser();

        String attributeBy = "attribute";
        List<ReportDataItem> data = List.of(
                new ReportDataItem("value1", 10),
                new ReportDataItem("value2", 5)
        );

        // When
        parser.write(data, attributeBy);

        // Then
        File file = new File(String.format("statistic_by_%s.xml", attributeBy));
        assertTrue(file.exists());

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] dataBytes = new byte[(int) file.length()];
            fis.read(dataBytes);
            String xmlContent = new String(dataBytes);

            assertTrue(xmlContent.contains("<item><value>value1</value><count>10</count></item>"));
            assertTrue(xmlContent.contains("<item><value>value2</value><count>5</count></item>"));
        }

        file.delete();
    }
}