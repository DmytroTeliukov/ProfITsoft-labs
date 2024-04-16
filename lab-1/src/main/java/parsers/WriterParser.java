package parsers;

import java.util.List;

public interface WriterParser<T> {
    /**
     * Writes the given report data to an XML file with the specified attribute.
     *
     * @param data the list of report data items
     * @param attributeBy the attribute to include in the XML filename and data
     */
    void write(List<T> data, String attributeBy);
}
