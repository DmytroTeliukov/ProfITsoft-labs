package parsers;

import java.util.List;

public interface ReaderParser<T> {
    /**
     * Reads attribute values from the specified JSON file based on the target field name.
     *
     * @param targetFieldName the name of the target field to extract values from
     * @param inputPath the path to the input JSON file
     * @return a list of attribute values extracted from the JSON file
     */
    List<T> read(String targetFieldName, String inputPath);
}
