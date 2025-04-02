package com.almaraz_john.user_service.domain.valueObjects;
import com.almaraz_john.user_service.domain.exception.InvalidPasswordFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordTest {
    @Test
    void testValidPasswordCreation() {
        // Arrange
        String rawPassword = "Password1$";

        // Act
        Password password = new Password(rawPassword);

        // Assert
        assertNotNull(password.getPasswordHash());
        assertTrue(password.matchesPassword(rawPassword));
    }

    @Test
    void testInvalidPasswordMissingUppercaseThrowsException() {
        // Arrange
        String rawPassword = "password1$";
        String messageExpected = "Invalid password. It must be at least 8 characters, " +
                "one uppercase letter, one lowercase letter, one number and one special character.";

        // Act
        InvalidPasswordFormatException exception = assertThrows(InvalidPasswordFormatException.class,
                ()-> new Password(rawPassword)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testInvalidPasswordMissingDigitThrowsException() {
        // Arrange
        String rawPassword = "Password$";
        String messageExpected = "Invalid password. It must be at least 8 characters, " +
                "one uppercase letter, one lowercase letter, one number and one special character.";

        // Act
        InvalidPasswordFormatException exception = assertThrows(InvalidPasswordFormatException.class,
                ()-> new Password(rawPassword)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testInvalidPasswordMissingSpecialCharacterThrowsException() {
        // Arrange
        String rawPassword = "Password1";
        String messageExpected = "Invalid password. It must be at least 8 characters, " +
                "one uppercase letter, one lowercase letter, one number and one special character.";

        // Act
        InvalidPasswordFormatException exception = assertThrows(InvalidPasswordFormatException.class,
                ()-> new Password(rawPassword)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testInvalidPasswordTooShortThrowsException() {
        // Arrange
        String rawPassword = "Pa$1";
        String messageExpected = "Invalid password. It must be at least 8 characters, " +
                "one uppercase letter, one lowercase letter, one number and one special character.";

        // Act
        InvalidPasswordFormatException exception = assertThrows(InvalidPasswordFormatException.class,
                ()-> new Password(rawPassword)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testOfHashWithValidHash() {
        // Arrange:
        String hash = "$2a$10$abcdefg1234567890abcdefg1234567890abcdefg1234567890ab";

        // Act
        Password password = Password.ofHash(hash);

        // Assert
        assertEquals(hash, password.getPasswordHash());
    }

    @Test
    void testOfHashWithNullThrowsException() {
        // Arrange
        String rawPassword = "Pa$1";
        String messageExpected = "Hash code cannot be null or empty";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> Password.ofHash(null)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testOfHashWithBlankThrowsException() {
        // Arrange
        String rawPassword = "Pa$1";
        String messageExpected = "Hash code cannot be null or empty";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> Password.ofHash("      ")
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }

    @Test
    void testMatchesPasswordCorrectly() {
        // Arrange
        String rawPassword = "Password1$";
        Password password = new Password(rawPassword);

        // Act & Assert
        assertTrue(password.matchesPassword(rawPassword));
        assertFalse(password.matchesPassword("WrongPassword1!"));
    }
}