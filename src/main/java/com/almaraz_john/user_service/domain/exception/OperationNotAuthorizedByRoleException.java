package com.almaraz_john.user_service.domain.exception;

public class OperationNotAuthorizedByRoleException extends RuntimeException {
    public OperationNotAuthorizedByRoleException(String message) {
        super(message);
    }
}
