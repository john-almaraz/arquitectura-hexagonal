package com.almaraz_john.user_service.common.mapper;

import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.Password;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserCommonMapperTest {

    private final UserCommonMapper mapper = new UserCommonMapper() {};

    @Test
    void testEmailToString() {
        // Arrange
        String emailString = "test@example.com";
        Email email = new Email(emailString);

        // Act
        String result = mapper.emailToString(email);

        // Assert
        assertEquals(emailString, result);
        assertNull(mapper.emailToString(null));
    }

    @Test
    void testStringToEmail() {
        // Arrange
        String emailString = "test@example.com";
        Email email = new Email(emailString);

        // Act
        Email result = mapper.stringToEmail(emailString);

        // Assert
        assertEquals(email, result);
        assertNull(mapper.stringToEmail(null));
    }

    @Test
    void testPasswordToString() {
        // Arrange
        String hash = "$2a$10$abcdefg1234567890abcdefg1234567890abcdefg1234567890ab";
        Password password = Password.ofHash(hash);

        // Act
        String result = mapper.passwordToString(password);

        // Assert
        assertEquals(hash, result);
        assertNull(mapper.passwordToString(null));
    }

    @Test
    void testStringToPassword() {
        // Arrange
        String rawPwd = "rawPassword1$";
        Password password = new Password(rawPwd);
        String hash = password.getPasswordHash();

        // Act
        Password result = mapper.stringToPassword(hash);

        // Assert
        assertEquals(password, result);
        assertNull(mapper.stringToPassword(null));
    }
}