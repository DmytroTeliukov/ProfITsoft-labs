package utils;

/**
 * A utility class for input validation.
 */
public class InputValidator {

    /**
     * Checks if a class does not contain a specified field.
     *
     * @param classData the class to check
     * @param fieldName the name of the field to check
     * @return true if the class does not contain the specified field, false otherwise
     */
    public static boolean notContainFieldToClass(Class<?> classData, String fieldName) {
        try {
            classData.getDeclaredField(fieldName); // if the field does not exist in the class, the method throws an exception
            return false;
        } catch (NoSuchFieldException e) {
            return true;
        }
    }

    /**
     * Checks if an integer is negative.
     *
     * @param num the number to check
     * @return true if the number is negative, false otherwise
     */
    public static boolean isNegative(int num) {
        return num < 0;
    }
}