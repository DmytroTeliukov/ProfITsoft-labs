package service;

public interface StatisticService {
    /**
     * Generates a report to a file based on the data in the specified folder,
     * analyzing it by the given attribute and using the specified count of threads.
     *
     * @param folderPath   the path to the folder containing JSON files
     * @param attributeBy  the attribute to analyze by
     * @param countThreads the count of threads to use for concurrent processing
     */
    void generateReportToFile(String folderPath, String attributeBy, int countThreads);
}
