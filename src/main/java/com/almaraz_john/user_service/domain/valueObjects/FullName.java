package com.almaraz_john.user_service.domain.valueObjects;


import com.almaraz_john.user_service.domain.exception.InvalidNameFormatException;

public record FullName(String firstName, String lastName) {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 15;

    public FullName {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("First name and last name cannot be null");
        }
        firstName = firstName.trim();
        lastName = lastName.trim();

        if (firstName.length() < MIN_LENGTH || firstName.length() > MAX_LENGTH) {
            throw new InvalidNameFormatException("First name must have between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters");
        }
        if (lastName.length() < MIN_LENGTH || lastName.length() > MAX_LENGTH) {
            throw new InvalidNameFormatException("Last name must have between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters");
        }

    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

}
