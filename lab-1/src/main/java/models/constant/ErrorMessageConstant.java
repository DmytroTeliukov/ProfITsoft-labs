package models.constant;

public class ErrorMessageConstant {
    public static final String REQUIRED_ARGUMENT_MISSING = """
            Usage: java JavaCoreDemo <directory_path> <attribute_name> [count_threads]
            """;

    public static final String COUNT_THREADS_IS_NOT_POSITIVE = "Count threads must be positive";
    public static final String ATTRIBUTE_NOT_EXIST_IN_DISH_CLASS = "The attribute does not exist in Dish class";
    public static final String FILE_ALREADY_EXIST = "File already exists";
    public static final String FOLDER_IS_EMPTY_BY_PATH = "Folder is empty by path: %s";
    public static final String JSON_FILES_NOT_EXISTS_BY_PATH = "Json files are not exist by path: %s";
}
