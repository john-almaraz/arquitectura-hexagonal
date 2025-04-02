package com.almaraz_john.user_service.infrastructure.adapters.output.persistence.mapper;

import com.almaraz_john.user_service.common.mapper.IdMapper;
import com.almaraz_john.user_service.common.mapper.UserCommonMapper;
import com.almaraz_john.user_service.domain.aggregate.User;
import com.almaraz_john.user_service.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = { IdMapper.class, UserCommonMapper.class })
public interface UserPersistenceMapper {

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "idToUUID"),
            @Mapping(target = "firstName", source = "fullName.firstName"),
            @Mapping(target = "lastName", source = "fullName.lastName"),
            @Mapping(target = "email", source = "email", qualifiedByName = "emailToString"),
            @Mapping(target = "password", source = "password", qualifiedByName = "passwordToString")
    })
    UserEntity toEntity(User user);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "uuidToID"),
            @Mapping(target = "fullName", expression = "java(new FullName(userEntity.getFirstName(),userEntity.getLastName()))"),
            @Mapping(target = "email", source = "email", qualifiedByName = "stringToEmail"),
            @Mapping(target = "password", source = "password", qualifiedByName = "stringToPassword")
    })
    User toDomain(UserEntity userEntity);

    List<User> toDomainList(List<UserEntity> userEntities);

}
