package com.almaraz_john.user_service.domain.exception;

public class InvalidNameFormatException extends RuntimeException {
  public InvalidNameFormatException(String message) {
    super(message);
  }
}
