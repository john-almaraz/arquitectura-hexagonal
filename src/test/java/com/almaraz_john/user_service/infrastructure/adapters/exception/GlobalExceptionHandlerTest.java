package com.almaraz_john.user_service.infrastructure.adapters.exception;

import com.almaraz_john.user_service.domain.exception.InvalidCommandException;
import com.almaraz_john.user_service.domain.exception.InvalidEmailFormatException;
import com.almaraz_john.user_service.domain.exception.InvalidNameFormatException;
import com.almaraz_john.user_service.domain.exception.InvalidPasswordFormatException;
import com.almaraz_john.user_service.domain.exception.OperationNotAuthorizedByRoleException;
import com.almaraz_john.user_service.domain.exception.PasswordNotMatchException;
import com.almaraz_john.user_service.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleUserNotFoundException() {
        // Arrange
        String errorMessage = "User not found";
        UserNotFoundException ex = new UserNotFoundException(errorMessage);

        // Act
        GlobalExceptionHandler.ErrorResponse response = exceptionHandler.handleUserNotFoundException(ex);

        // Assert
        assertEquals(errorMessage, response.getMessage());
        assertTrue(response.getTimestamp() > 0);
    }

    @Test
    void testHandleInvalidEmailFormatException() {
        // Arrange
        String errorMessage = "Invalid email format";
        InvalidEmailFormatException ex = new InvalidEmailFormatException(errorMessage);

        // Act
        GlobalExceptionHandler.ErrorResponse response = exceptionHandler.handleInvalidEmailFormatException(ex);

        // Assert
        assertEquals(errorMessage, response.getMessage());
        assertTrue(response.getTimestamp() > 0);
    }

    @Test
    void testHandleInvalidNameFormatException() {
        // Arrange
        String errorMessage = "Invalid name format";
        InvalidNameFormatException ex = new InvalidNameFormatException(errorMessage);

        // Act
        GlobalExceptionHandler.ErrorResponse response = exceptionHandler.handleInvalidNameFormatException(ex);

        // Assert
        assertEquals(errorMessage, response.getMessage());
        assertTrue(response.getTimestamp() > 0);
    }

    @Test
    void testHandleInvalidPasswordFormatException() {
        // Arrange
        String errorMessage = "Invalid password format";
        InvalidPasswordFormatException ex = new InvalidPasswordFormatException(errorMessage);

        // Act
        GlobalExceptionHandler.ErrorResponse response = exceptionHandler.handleInvalidPasswordFormatException(ex);

        // Assert
        assertEquals(errorMessage, response.getMessage());
        assertTrue(response.getTimestamp() > 0);
    }

    @Test
    void testHandleInvalidCommandException() {
        // Arrange
        String errorMessage = "Invalid command";
        InvalidCommandException ex = new InvalidCommandException(errorMessage);

        // Act
        GlobalExceptionHandler.ErrorResponse response = exceptionHandler.handleInvalidCommandException(ex);

        // Assert
        assertEquals(errorMessage, response.getMessage());
        assertTrue(response.getTimestamp() > 0);
    }

    @Test
    void testHandleOperationNotAuthorizedByRoleException() {
        // Arrange
        String errorMessage = "Operation not authorized";
        OperationNotAuthorizedByRoleException ex = new OperationNotAuthorizedByRoleException(errorMessage);

        // Act
        GlobalExceptionHandler.ErrorResponse response = exceptionHandler.handleOperationNotAuthorizedByRoleException(ex);

        // Assert
        assertEquals(errorMessage, response.getMessage());
        assertTrue(response.getTimestamp() > 0);
    }

    @Test
    void testHandlePasswordNotMatchException() {
        // Arrange
        String errorMessage = "Password does not match";
        PasswordNotMatchException ex = new PasswordNotMatchException(errorMessage);

        // Act
        GlobalExceptionHandler.ErrorResponse response = exceptionHandler.handlePasswordNotMatchException(ex);

        // Assert
        assertEquals(errorMessage, response.getMessage());
        assertTrue(response.getTimestamp() > 0);
    }
}