package com.almaraz_john.user_service.domain.valueObjects;

import com.almaraz_john.user_service.domain.exception.InvalidNameFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FullNameTest {
    //COMMON VARIABLE
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 15;

    @Test
    void testValidFullName() {
        // Arrange
        String toString = FIRST_NAME + " " +LAST_NAME;

        // Act
        FullName fullName = new FullName(FIRST_NAME,LAST_NAME);

        // Assert
        assertEquals(FIRST_NAME, fullName.firstName());
        assertEquals(LAST_NAME, fullName.lastName());
        assertEquals(toString, fullName.toString());
    }

    @Test
    void testNullFirstNameThrowsException() {
        // Arrange
        String messageExpected = "First name and last name cannot be null";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> new FullName(null,LAST_NAME)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testNullLastNameThrowsException() {
        // Arrange
        String messageExpected = "First name and last name cannot be null";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> new FullName(FIRST_NAME,null)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testTrimOfNames() {
        // Arrange
        String firstName = "   John    ";
        String lastName = "     Smith    ";

        // Act
        FullName fullName = new FullName(firstName,lastName);

        // Assert
        assertEquals(firstName.trim(), fullName.firstName());
        assertEquals(lastName.trim(), fullName.lastName());
    }

    @Test
    void testFirstNameTooShortThrowsException() {
        // Arrange
        String firstName = "J";
        String messageExpected = "First name must have between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters";

        // Act
        InvalidNameFormatException exception = assertThrows(InvalidNameFormatException.class,
                ()-> new FullName(firstName,LAST_NAME)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testFirstNameTooLongThrowsException() {
        // Arrange
        String firstName = "ThisNameIsToLarge";
        String messageExpected = "First name must have between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters";

        // Act
        InvalidNameFormatException exception = assertThrows(InvalidNameFormatException.class,
                ()-> new FullName(firstName,LAST_NAME)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testLastNameTooShortThrowsException() {
        // Arrange
        String lastName = "S";
        String messageExpected = "Last name must have between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters";

        // Act
        InvalidNameFormatException exception = assertThrows(InvalidNameFormatException.class,
                ()-> new FullName(FIRST_NAME,lastName)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testLastNameTooLongThrowsException() {
        // Arrange
        String lastName = "ThisLastNameIsToLarge";
        String messageExpected = "Last name must have between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters";

        // Act
        InvalidNameFormatException exception = assertThrows(InvalidNameFormatException.class,
                ()-> new FullName(FIRST_NAME,lastName)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

}