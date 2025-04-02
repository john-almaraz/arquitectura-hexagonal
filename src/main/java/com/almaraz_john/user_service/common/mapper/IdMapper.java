package com.almaraz_john.user_service.common.mapper;

import com.almaraz_john.user_service.domain.valueObjects.ID;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IdMapper {
    @Named("idToUUID")
    default UUID idToUUID(ID id) {
        return id == null ? null : id.getValue();
    }

    @Named("uuidToID")
    default ID uuidToID(UUID uuid) {
        return uuid == null ? null : ID.of(uuid);
    }
}
