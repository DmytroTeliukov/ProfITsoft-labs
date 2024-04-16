package service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parsers.impl.XmlReportDataWriterParser;
import service.AnalyticService;
import service.StatisticService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class StatisticServiceImplTest {
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
    @DisplayName("should generate file with report successfully when call the method")
    public void shouldGenerateFileWithReportSuccessfully_whenCallTheMethod() {
        // Given
        XmlReportDataWriterParser xmlParser = mock(XmlReportDataWriterParser.class);
        AnalyticService analyticService = mock(AnalyticService.class);
        StatisticService statisticService = new StatisticServiceImpl(xmlParser, analyticService);
        Map<String, Integer> statistic = Map.of("value1", 10, "value2", 5); // Sample statistic data
        int countThreads = 4;
        String attributeBy = "category";
        when(analyticService.analyzeDataFromFile(INPUT_FILE_PATHS, attributeBy, countThreads)).thenReturn(statistic);

        // When
        statisticService.generateReportToFile(INPUT_PATH, attributeBy, countThreads);

        // Then
        verify(xmlParser, times(1)).write(any(), eq(attributeBy));
    }

}