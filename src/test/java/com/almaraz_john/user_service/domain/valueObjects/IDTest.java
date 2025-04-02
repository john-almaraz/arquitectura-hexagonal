package com.almaraz_john.user_service.domain.valueObjects;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IDTest {
    @Test
    void testCreateGeneratesValidID() {
        // Act
        ID id = ID.create();

        // Assert
        assertNotNull(id);
        assertNotNull(id.getValue());
    }

    @Test
    void testOfWithValidUUID() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        ID id = ID.of(uuid);

        // Assert
        assertEquals(uuid, id.getValue());
    }

    @Test
    void testOfWithNullThrowsException() {
        // Arrange
        String messageExpected = "The identifier cannot be null";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> ID.of(null)
        );

        // Assert
        assertEquals(messageExpected,exception.getMessage());
    }
}