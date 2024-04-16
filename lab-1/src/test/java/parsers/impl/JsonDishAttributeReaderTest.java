package parsers.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for JsonDishAttributeReader class.
 */
public class JsonDishAttributeReaderTest {
    public static final String INPUT_PATH = "src/test/resources/dataset/case_1/test.json";


    @Test
    @SneakyThrows
    @DisplayName("should extract data from text field successfully when read json file")
    public void shouldExtractDataFromTextFieldSuccessfully_whenReadJsonFile() {
        // Given
        JsonDishAttributeReader reader = new JsonDishAttributeReader();
        JsonParser parser = Mockito.mock(JsonParser.class);
        when(parser.getCurrentToken()).thenReturn(JsonToken.START_OBJECT,
                JsonToken.FIELD_NAME,
                JsonToken.VALUE_STRING,
                JsonToken.END_OBJECT,
                null);
        when(parser.getCurrentName()).thenReturn("category");
        when(parser.getText()).thenReturn("Pizza");

        // When
        List<String> result = reader.read("category", INPUT_PATH);

        // Then
        assertEquals(3, result.size());
        assertEquals("Main Course", result.get(0));
    }

    @Test
    @SneakyThrows
    @DisplayName("should extract data from array field successfully when read json file")
    public void shouldExtractDataFromArrayFieldSuccessfully_whenReadJsonFile() {
        // Given
        JsonDishAttributeReader reader = new JsonDishAttributeReader();
        JsonParser parser = Mockito.mock(JsonParser.class);
        when(parser.getCurrentToken()).thenReturn(JsonToken.START_OBJECT,
                JsonToken.FIELD_NAME,
                JsonToken.START_ARRAY,
                JsonToken.VALUE_STRING,
                JsonToken.VALUE_STRING,
                JsonToken.END_ARRAY,
                JsonToken.END_OBJECT,
                null);
        when(parser.getCurrentName()).thenReturn("ingredients");
        when(parser.getText()).thenReturn("Flour", "Tomato");

        // When
        List<String> result = reader.read("ingredients", INPUT_PATH);

        // Then
        assertEquals(13, result.size());
        assertEquals("Spaghetti", result.get(0));
        assertEquals("Bacon", result.get(1));
    }

    @Test
    @SneakyThrows
    @DisplayName("should extract data from non-exist field successfully when read json file")
    public void shouldExtractDataFromNonExistFieldSuccessfully_whenReadJsonFile() {
        // Given
        JsonDishAttributeReader reader = new JsonDishAttributeReader();
        JsonParser parser = Mockito.mock(JsonParser.class);
        when(parser.getCurrentToken()).thenReturn(JsonToken.START_OBJECT,
                JsonToken.FIELD_NAME,
                JsonToken.VALUE_STRING,
                JsonToken.END_OBJECT,
                null);
        when(parser.getCurrentName()).thenReturn("name");
        when(parser.getText()).thenReturn("Pizza");

        // When
        List<String> result = reader.read("invalidField", INPUT_PATH);

        // Then
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("should throw RuntimeException when read json file")
    public void shouldThrowRuntimeException_whenReadJsonFile() {
        // Given
        JsonDishAttributeReader reader = new JsonDishAttributeReader();

        // When & Then
        assertThrows(RuntimeException.class, () -> reader.read("name", "nonexistent.json"));
    }
}