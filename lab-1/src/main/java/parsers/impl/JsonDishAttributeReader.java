package parsers.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import parsers.ReaderParser;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A reader/parser implementation for extracting attribute values from JSON dishes.
 */
public class JsonDishAttributeReader implements ReaderParser<String> {

    /**
     * The JSON factory for creating JSON parsers.
     */
    private static final JsonFactory JSON_FACTORY = new JsonFactory();

    /**
     * The set of JSON fields representing arrays in a dish.
     */
    private static final Set<String> DISH_ARRAY_FIELDS = new HashSet<>();

    static {
        DISH_ARRAY_FIELDS.add("specifics");
        DISH_ARRAY_FIELDS.add("cuisines");
        DISH_ARRAY_FIELDS.add("ingredients");
    }

    @Override
    public List<String> read(String targetFieldName, String inputPath) {
        List<String> attributeValues = new LinkedList<>();

        try (JsonParser parser = JSON_FACTORY.createParser(new File(inputPath))) {
            while (parser.nextToken() != null) {
                if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
                    while (parser.nextToken() != JsonToken.END_OBJECT) {
                        String actualFieldName = parser.getCurrentName();
                        if (isTargetFieldName(actualFieldName, targetFieldName)) {
                            if (DISH_ARRAY_FIELDS.contains(actualFieldName)) {
                                handleArrayAttribute(parser, attributeValues);
                            } else {
                                handleTextAttribute(parser, attributeValues);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return attributeValues;
    }

    /**
     * Handles a text attribute in the JSON parser context.
     *
     * @param parser          the JSON parser
     * @param attributeValues the list to store attribute values
     * @throws IOException if an I/O error occurs during parsing
     */
    private void handleTextAttribute(JsonParser parser, List<String> attributeValues) throws IOException {
        parser.nextToken();
        attributeValues.add(parser.getText());
    }

    /**
     * Handles an array attribute in the JSON parser context.
     *
     * @param parser          the JSON parser
     * @param attributeValues the list to store attribute values
     * @throws IOException if an I/O error occurs during parsing
     */
    private void handleArrayAttribute(JsonParser parser, List<String> attributeValues) throws IOException {
        parser.nextToken();
        if (parser.getCurrentToken() == JsonToken.START_ARRAY) {
            parser.nextToken();
        }
        while (parser.getCurrentToken() != JsonToken.END_ARRAY) {
            attributeValues.add(parser.getText());
            parser.nextToken();
        }
    }

    /**
     * Checks if the actual field name matches the target field name.
     *
     * @param actualFieldName the actual field name
     * @param targetFieldName the target field name
     * @return true if the actual field name matches the target field name, otherwise false
     */
    private boolean isTargetFieldName(String actualFieldName, String targetFieldName) {
        return targetFieldName.equals(actualFieldName);
    }
}
