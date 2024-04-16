package demo;

import models.constant.ErrorMessageConstant;
import models.domain.Dish;
import parsers.impl.JsonDishAttributeReader;
import parsers.impl.XmlReportDataWriterParser;
import service.AnalyticService;
import service.StatisticService;
import service.impl.AnalyticServiceImpl;
import service.impl.StatisticServiceImpl;
import utils.InputValidator;

/**
 * A demonstration class showcasing core Java functionality.
 */
public class JavaCoreDemo {

    /** The required count of command line arguments. */
    private static final int REQUIRED_COUNT_ARGS = 2;

    /** The count of command line arguments when count_threads is provided. */
    private static final int ALL_COUNT_ARGS = 3;

    /** The default count of threads if not provided in command line arguments. */
    private static final int DEFAULT_COUNT_THREADS = 1;

    /**
     * The main entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            if (args.length < REQUIRED_COUNT_ARGS) {
                throw new IllegalArgumentException(ErrorMessageConstant.REQUIRED_ARGUMENT_MISSING);
            }

            /* Retrieve values from arguments */
            String folderPath = args[0];
            String attribute = args[1];
            int countThreads = DEFAULT_COUNT_THREADS; // Default value if count_threads is not provided

            JsonDishAttributeReader jsonDishAttributeReader = new JsonDishAttributeReader();
            AnalyticService analyticService = new AnalyticServiceImpl(jsonDishAttributeReader);
            XmlReportDataWriterParser xmlReportDataWriterParser = new XmlReportDataWriterParser();
            StatisticService statisticService = new StatisticServiceImpl(xmlReportDataWriterParser, analyticService);

            if (args.length == ALL_COUNT_ARGS) {
                countThreads = Integer.parseInt(args[2]);
            }

            if (InputValidator.isNegative(countThreads)) {
                throw new IllegalArgumentException(ErrorMessageConstant.COUNT_THREADS_IS_NOT_POSITIVE);
            } else if (InputValidator.notContainFieldToClass(Dish.class, attribute)) {
                throw new IllegalArgumentException(ErrorMessageConstant.ATTRIBUTE_NOT_EXIST_IN_DISH_CLASS);
            }

            statisticService.generateReportToFile(folderPath, attribute, countThreads);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}