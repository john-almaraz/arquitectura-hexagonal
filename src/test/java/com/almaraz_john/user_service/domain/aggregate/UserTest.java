package com.almaraz_john.user_service.domain.aggregate;

import com.almaraz_john.user_service.application.dto.UserDTO;
import com.almaraz_john.user_service.domain.enums.Role;
import com.almaraz_john.user_service.domain.exception.PasswordNotMatchException;
import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.FullName;
import com.almaraz_john.user_service.domain.valueObjects.ID;
import com.almaraz_john.user_service.domain.valueObjects.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    //COMMON VARIABLES
    private static final UUID USER_ID_INVALID =UUID.fromString("d6037627-e89e-4556-b78c-e7d98bf097c7");
    private static final UUID USER_ID = UUID.fromString("37e3ca99-fe84-4959-92d8-4f25fa73e65f");
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Almaraz";
    private static final String USER_EMAIL = "john_almaraz123@gmail.es";
    private static final String USER_PASSWORD = "JohnAlmaraz$123";
    private User user;

    @BeforeEach
    void setup(){
        user = new User(
                ID.of(USER_ID),
                new FullName(USER_FIRST_NAME,USER_LAST_NAME),
                new Email(USER_EMAIL),
                new Password(USER_PASSWORD),
                Role.ADMIN
        );
    }

    @Nested
    @DisplayName("UpdatePassword Tests")
    class UpdatePasswordTests{
        @Test
        public void testUpdatePasswordCorrect() {
            // Arrange
            String newPwd = "newPwd123$";

            // Act
            user.updatePassword(USER_PASSWORD,newPwd);

            // Assert
            assertTrue(user.getPassword().matchesPassword(newPwd));
        }

        @Test
        public void testUpdatePasswordIncorrect() {
            // Arrange
            String newPwd = "newPwd123$";
            String messageExpected = "Password is incorrect";

            // Act
            PasswordNotMatchException exception = assertThrows(PasswordNotMatchException.class,
                    () -> user.updatePassword("wrongPassword", newPwd)
            );

            // Assert
            assertEquals(messageExpected,exception.getMessage());
        }
    }

    @Nested
    @DisplayName("UpdateUserProfile Tests")
    class UpdateUserProfileTests{
        @Test
        public void testUpdateUserProfileWithFullNameAndEmail() {
            // Arrange
            String newFirstName = "Henry";
            String newLastName = "Smith";
            String newEmail = "henry_smith1@hotmail.com";
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(newFirstName);
            userDTO.setLastName(newLastName);
            userDTO.setEmail(newEmail);

            // Act
            user.updateUserProfile(userDTO);

            // Assert
            assertEquals(newFirstName, user.getFullName().firstName());
            assertEquals(newLastName, user.getFullName().lastName());
            assertEquals(newEmail, user.getEmail().email());
        }

        @Test
        public void testUpdateUserProfileOnlyFirstName() {
            // Arrange
            String newFirstName = "Henry";
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(newFirstName);

            // Act
            user.updateUserProfile(userDTO);

            // Assert
            assertEquals(newFirstName, user.getFullName().firstName());
            assertEquals(USER_LAST_NAME, user.getFullName().lastName());
        }

        @Test
        public void testUpdateUserProfileOnlyLastName() {
            // Arrange
            String newLastName = "Smith";
            UserDTO userDTO = new UserDTO();
            userDTO.setLastName(newLastName);

            // Act
            user.updateUserProfile(userDTO);

            // Assert
            assertEquals(USER_FIRST_NAME, user.getFullName().firstName());
            assertEquals(newLastName, user.getFullName().lastName());
        }

        @Test
        public void testUpdateUserProfileOnlyEmail() {
            // Arrange
            String newEmail = "newEmail@hotmail.com";
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(newEmail);

            // Act
            user.updateUserProfile(userDTO);

            // Assert
            assertEquals(USER_FIRST_NAME, user.getFullName().firstName());
            assertEquals(USER_LAST_NAME, user.getFullName().lastName());
            assertEquals(newEmail, user.getEmail().email());
        }
    }

}