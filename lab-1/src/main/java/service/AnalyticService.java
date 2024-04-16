package service;

import java.util.List;
import java.util.Map;

public interface AnalyticService {
    /**
     * Analyzes data from files based on the specified input paths, attribute to analyze by,
     * and the count of threads to use for concurrent processing.
     *
     * @param inputPaths   the list of input file paths
     * @param attributeBy  the attribute to analyze by
     * @param countThreads the count of threads to use for concurrent processing
     * @return a map containing the attribute occurrences and their counts
     */
    Map<String, Integer> analyzeDataFromFile(List<String> inputPaths, String attributeBy, int countThreads);
}
