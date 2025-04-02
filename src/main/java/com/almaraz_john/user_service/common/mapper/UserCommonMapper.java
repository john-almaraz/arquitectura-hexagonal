package com.almaraz_john.user_service.common.mapper;

import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.Password;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserCommonMapper {

    @Named("emailToString")
    default String emailToString(Email email) {
        return email == null ? null : email.email();
    }

    @Named("stringToEmail")
    default Email stringToEmail(String email) {
        return email == null ? null : new Email(email);
    }

    @Named("passwordToString")
    default String passwordToString(Password password) {
        return password == null ? null : password.getPasswordHash();
    }

    @Named("stringToPassword")
    default Password stringToPassword(String password) {
        return password == null ? null : Password.ofHash(password);
    }
}
