package com.almaraz_john.user_service.infrastructure.adapters.exception;

import com.almaraz_john.user_service.domain.exception.InvalidCommandException;
import com.almaraz_john.user_service.domain.exception.InvalidEmailFormatException;
import com.almaraz_john.user_service.domain.exception.InvalidNameFormatException;
import com.almaraz_john.user_service.domain.exception.InvalidPasswordFormatException;
import com.almaraz_john.user_service.domain.exception.OperationNotAuthorizedByRoleException;
import com.almaraz_john.user_service.domain.exception.PasswordNotMatchException;
import com.almaraz_john.user_service.domain.exception.UserNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Getter
    public static class ErrorResponse {
        private final String message;
        private final long timestamp;

        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidEmailFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidEmailFormatException(InvalidEmailFormatException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidNameFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidNameFormatException(InvalidNameFormatException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidPasswordFormatException(InvalidPasswordFormatException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidCommandException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidCommandException(InvalidCommandException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(OperationNotAuthorizedByRoleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleOperationNotAuthorizedByRoleException(OperationNotAuthorizedByRoleException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePasswordNotMatchException(PasswordNotMatchException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
