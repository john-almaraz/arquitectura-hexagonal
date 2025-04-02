package com.almaraz_john.user_service.domain.valueObjects;

import com.almaraz_john.user_service.domain.exception.InvalidEmailFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    public void testValidEmail() {
        // Arrange
        String validEmail = "test.email@example.com";

        // Act
        Email email = new Email(validEmail);

        // Assert
        assertEquals(validEmail, email.email());
    }

    @Test
    public void testNullEmail() {
        // Arrange
        String messageExpected = "Email cannot be null or empty";

        // Act
        InvalidEmailFormatException exception = assertThrows(InvalidEmailFormatException.class,
                ()-> new Email(null)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    public void testBlankEmail() {
        // Arrange
        String messageExpected = "Email cannot be null or empty";

        // Act
        InvalidEmailFormatException exception = assertThrows(InvalidEmailFormatException.class,
                ()-> new Email("     ")
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    public void testInvalidEmailFormat() {
        // Arrange
        String invalidEmail = "invalidFormat";
        String messageExpected = "Invalid email format";

        // Act
        InvalidEmailFormatException exception = assertThrows(InvalidEmailFormatException.class,
                ()-> new Email(invalidEmail)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }
}