package service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parsers.impl.JsonDishAttributeReader;
import service.AnalyticService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class AnalyticServiceImplTest {
    private static final String INPUT_PATH = "src/test/resources/dataset/case_2/";
    private static final List<String> INPUT_FILE_PATHS = new LinkedList<>();

    @BeforeAll
    public static void setUp() {
        // Given
        INPUT_FILE_PATHS.add(INPUT_PATH + "first_three_dishes.json");
        INPUT_FILE_PATHS.add(INPUT_PATH + "second_three_dishes.json");
        INPUT_FILE_PATHS.add(INPUT_PATH + "third_three_dishes.json");
        INPUT_FILE_PATHS.add(INPUT_PATH + "fourth_three_dishes.json");
    }

    @Test
    @DisplayName("should return data from analyzing file successfully when analyze file in single-thread")
    public void shouldReturnDataFromAnalyzingFileSuccessfully_whenAnalyzeFileInSingleThread() {
        // Given
        JsonDishAttributeReader reader = new JsonDishAttributeReader();
        AnalyticService analyticService = new AnalyticServiceImpl(reader);

        // When
        Map<String, Integer> result = analyticService.analyzeDataFromFile(INPUT_FILE_PATHS,
                "category", 1);

        // Then
        assertEquals(2, result.size());
        assertEquals(8, result.get("Main Course"));
    }

    @Test
    @DisplayName("should return data from analyzing file successfully when analyze file in multi-thread")
    public void shouldReturnDataFromAnalyzingFileSuccessfully_whenAnalyzeFileInMultiThread() {
        // Given
        JsonDishAttributeReader reader = new JsonDishAttributeReader();
        AnalyticService analyticService = new AnalyticServiceImpl(reader);

        // When
        Map<String, Integer> result = analyticService.analyzeDataFromFile(INPUT_FILE_PATHS,
                "ingredients", 4);

        // Then
        assertEquals(12, result.size());
        assertEquals(8, result.get("Eggs"));
    }

}