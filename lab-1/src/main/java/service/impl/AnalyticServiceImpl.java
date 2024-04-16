package service.impl;

import parsers.impl.JsonDishAttributeReader;
import service.AnalyticService;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * An implementation of the AnalyticService interface for analyzing data from files.
 */
public class AnalyticServiceImpl implements AnalyticService {
    /**
     * The maximum count of threads to be used for concurrent processing.
     */
    private static final int MAX_COUNT_THREADS = 8;

    /**
     * The parser used to read JSON attributes from dishes.
     */
    private final JsonDishAttributeReader dishFieldsJacksonParser;

    /**
     * A map to store statistics of attribute occurrences.
     */
    Map<String, Integer> statistic;

    /**
     * Constructs an AnalyticServiceImpl with the specified JSON dish attribute reader.
     *
     * @param dishFieldsJacksonParser the JSON dish attribute reader to use
     */
    public AnalyticServiceImpl(JsonDishAttributeReader dishFieldsJacksonParser) {
        this.dishFieldsJacksonParser = dishFieldsJacksonParser;
        this.statistic = new ConcurrentHashMap<>();
    }

    @Override
    public Map<String, Integer> analyzeDataFromFile(List<String> inputPaths, String attributeBy, int countThreads) {
        /* Limit count of threads if necessary */
        countThreads = Math.min(MAX_COUNT_THREADS, Math.min(inputPaths.size(), countThreads));

        /* Count occurrences by attribute from files */
        countOccurrencesByAttributeFromFile(inputPaths, countThreads, attributeBy);

        /* Create a sorted copy of the statistic data */
        Map<String, Integer> copiedStatisticData = Map.copyOf(this.statistic);
        copiedStatisticData = copiedStatisticData.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (value, count) -> value, LinkedHashMap::new));

        /* Clear the statistic map */
        statistic.clear();

        return copiedStatisticData;
    }

    /**
     * Counts occurrences of attribute values from files using concurrent processing.
     *
     * @param inputPaths   the list of input file paths
     * @param countThreads the count of threads to use for concurrent processing
     * @param attributeBy  the attribute to analyze by
     */
    private void countOccurrencesByAttributeFromFile(List<String> inputPaths,
                                                     int countThreads,
                                                     String attributeBy) {
        ExecutorService executor = Executors.newFixedThreadPool(countThreads);
        CompletionService<List<String>> completionService = new ExecutorCompletionService<>(executor);

        /* Submit tasks to read attribute values from files */
        for (String inputPath : inputPaths) {
            completionService.submit(() -> dishFieldsJacksonParser.read(attributeBy, inputPath));
        }

        /* Process results from completed tasks */
        for (int i = 0; i < inputPaths.size(); i++) {
            try {
                Future<List<String>> future = completionService.take();
                List<String> attributeValues = future.get();
                updateStatistic(attributeValues);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown(); // Shutdown executor service
                try {
                    if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                        executor.shutdownNow(); // Force shutdown if waiting times out
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow(); // Preserve interrupt
                }
            }
        }
    }

    /**
     * Updates the statistic with counts of attribute values.
     *
     * @param attributeValues the list of attribute values
     */
    private void updateStatistic(List<String> attributeValues) {
        attributeValues.stream()
                .collect(Collectors.toMap(key -> key, key -> 1, Integer::sum))
                .forEach((key, value) ->
                        this.statistic.put(key, this.statistic.getOrDefault(key, 0) + value));
    }
}
