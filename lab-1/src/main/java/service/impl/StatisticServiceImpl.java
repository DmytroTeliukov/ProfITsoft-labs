package service.impl;

import models.constant.ErrorMessageConstant;
import models.common.ReportDataItem;
import parsers.impl.XmlReportDataWriterParser;
import service.AnalyticService;
import service.StatisticService;

import java.io.File;
import java.util.*;

/**
 * An implementation of the StatisticService interface for generating reports based on analyzed data.
 */
public class StatisticServiceImpl implements StatisticService {
    /**
     * The file extension for JSON files.
     */
    private static final String JSON_FILE_EXTENSION = ".json";

    /**
     * The XML report data writer/parser.
     */
    private final XmlReportDataWriterParser xmlReportDataWriterParser;

    /**
     * The analytic service used for data analysis.
     */
    private final AnalyticService analyticService;

    /**
     * Constructs a StatisticServiceImpl with the specified XML report data writer/parser
     * and analytic service.
     *
     * @param xmlReportDataWriterParser the XML report data writer/parser to use
     * @param analyticService the analytic service to use for data analysis
     */
    public StatisticServiceImpl(XmlReportDataWriterParser xmlReportDataWriterParser, AnalyticService analyticService) {
        this.xmlReportDataWriterParser = xmlReportDataWriterParser;
        this.analyticService = analyticService;
    }

    @Override
    public void generateReportToFile(String folderPath,
                                     String attributeBy,
                                     int countThreads) {
        List<String> inputPaths = extractJsonFilePaths(folderPath);
        Map<String, Integer> statistic = analyticService.analyzeDataFromFile(inputPaths, attributeBy, countThreads);
        List<ReportDataItem> reportDataItems = statistic.entrySet().stream()
                .map((data) -> new ReportDataItem(data.getKey(), data.getValue()))
                .toList();

        xmlReportDataWriterParser.write(reportDataItems, attributeBy);
    }

    /**
     * Extracts the paths of JSON files from the specified folder path.
     *
     * @param folderPath the path to the folder containing JSON files
     * @return a list of paths to JSON files in the specified folder
     * @throws IllegalArgumentException if the folder path is empty or does not exist
     * @throws NoSuchElementException   if no JSON files exist in the specified folder
     */
    private List<String> extractJsonFilePaths(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.isDirectory()) {
            throw new IllegalArgumentException(String.format(ErrorMessageConstant.FOLDER_IS_EMPTY_BY_PATH, folderPath));
        }

        List<String> inputPaths = new LinkedList<>();

        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(file -> file.isFile() && file.getName().endsWith(JSON_FILE_EXTENSION))
                .map(File::getPath)
                .forEach(inputPaths::add);

        if (inputPaths.isEmpty()) {
            throw new NoSuchElementException(
                    String.format(ErrorMessageConstant.JSON_FILES_NOT_EXISTS_BY_PATH, folderPath));
        }

        return inputPaths;
    }
}
