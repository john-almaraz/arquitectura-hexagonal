package com.almaraz_john.user_service.common.mapper;

import com.almaraz_john.user_service.domain.valueObjects.ID;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IdMapperTest {

    private final IdMapper mapper = new IdMapper() {};

    @Test
    void testIdToUUID() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        ID id = ID.of(uuid);

        // Act
        UUID result = mapper.idToUUID(id);

        // Assert
        assertEquals(uuid, result);
        assertNull(mapper.idToUUID(null));
    }

    @Test
    void testUuidToID() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        ID result = mapper.uuidToID(uuid);

        // Assert
        assertEquals(uuid, result.getValue());
        assertNull(mapper.uuidToID(null));
    }
}