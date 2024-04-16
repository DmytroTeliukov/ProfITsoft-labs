package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {

    @Test
    @DisplayName("should return false when existing field is present")
    public void shouldReturnFalse_whenExistingFieldIsPresent() {
        // Given
        // When
        // Then
        assertFalse(InputValidator.notContainFieldToClass(TestClass.class, "existingField"));
    }

    @Test
    @DisplayName("should return true when non-existing field is not present")
    public void shouldReturnTrue_whenNonExistingFieldIsNotPresent() {
        // Given
        // When
        // Then
        assertTrue(InputValidator.notContainFieldToClass(TestClass.class, "nonExistingField"));
    }

    @Test
    @DisplayName("should throw NullPointerException when class is null")
    public void shouldThrowNullPointerException_whenClassIsNull() {
        // Given
        // When
        // Then
        assertThrows(NullPointerException.class, () -> InputValidator.notContainFieldToClass(null, "fieldName"));
    }

    @Test
    @DisplayName("should return false when number is positive")
    public void shouldReturnFalse_whenNumberIsPositive() {
        // Given
        // When
        // Then
        assertFalse(InputValidator.isNegative(5));
    }

    @Test
    @DisplayName("should return true when number is negative")
    public void shouldReturnTrue_whenNumberIsNegative() {
        // Given
        // When
        // Then
        assertTrue(InputValidator.isNegative(-5));
    }

    @Test

    @DisplayName("should return false when number is zero")
    public void shouldReturnTrue_whenNumberIsZero() {
        // Given
        // When
        // Then
        assertFalse(InputValidator.isNegative(0));
    }

    @Test
    @DisplayName("should return false when number is max integer value")
    public void shouldReturnFalse_whenNumberIsMaxIntegerValue() {
        // Given
        // When
        // Then
        assertFalse(InputValidator.isNegative(Integer.MAX_VALUE));
    }

    /* Sample class for unit tests*/
    private static class TestClass {
        private int existingField;
    }
}