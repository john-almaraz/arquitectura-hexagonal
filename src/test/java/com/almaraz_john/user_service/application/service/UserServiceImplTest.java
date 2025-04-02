package com.almaraz_john.user_service.application.service;

import com.almaraz_john.user_service.application.command.UserCreateCommand;
import com.almaraz_john.user_service.application.command.UserDeleteCommand;
import com.almaraz_john.user_service.application.command.UserGetByEmailCommand;
import com.almaraz_john.user_service.application.command.UserGetByIdCommand;
import com.almaraz_john.user_service.application.command.UserUpdateCommand;
import com.almaraz_john.user_service.application.command.UserUpdatePasswordCommand;
import com.almaraz_john.user_service.application.dto.UserDTO;
import com.almaraz_john.user_service.application.mapper.UserMapper;
import com.almaraz_john.user_service.application.port.output.UserRepository;
import com.almaraz_john.user_service.domain.aggregate.User;
import com.almaraz_john.user_service.domain.enums.Role;
import com.almaraz_john.user_service.domain.exception.OperationNotAuthorizedByRoleException;
import com.almaraz_john.user_service.domain.exception.UserNotFoundException;
import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.FullName;
import com.almaraz_john.user_service.domain.valueObjects.ID;
import com.almaraz_john.user_service.domain.valueObjects.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    //COMMON VARIABLES
    private static final UUID USER_ID_INVALID =UUID.fromString("d6037627-e89e-4556-b78c-e7d98bf097c7");
    private static final UUID USER_ID = UUID.fromString("37e3ca99-fe84-4959-92d8-4f25fa73e65f");
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Almaraz";
    private static final String USER_EMAIL = "john_almaraz123@gmail.es";
    private static final String USER_PASSWORD = "JohnAlmaraz$123";
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setup(){
        user = new User(
                ID.of(USER_ID),
                new FullName(USER_FIRST_NAME,USER_LAST_NAME),
                new Email(USER_EMAIL),
                new Password(USER_PASSWORD),
                Role.ADMIN
        );

        userDTO = new UserDTO(
                USER_ID,
                USER_FIRST_NAME,
                USER_LAST_NAME,
                USER_EMAIL,
                USER_PASSWORD,
                Role.ADMIN
        );
    }

    @Nested
    @DisplayName("Create Tests")
    class CreateTests{
        @Test
        public void createUser_ShouldReturnUser_WhenUserIsCreated(){
            //Arrange
            UserCreateCommand command = new UserCreateCommand(userDTO);

            when(userRepository.create(any(User.class))).thenReturn(user);
            when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

            //Act
            UserDTO result = userService.create(command);

            //Assert
            assertEquals(userDTO,result);

            verify(userRepository).create(any(User.class));
            verify(userMapper).toDTO(any(User.class));
        }
    }

    @Nested
    @DisplayName("GetUserByID Tests")
    class GetUserByIdTests{
        @Test
        public void getUserByID_ShouldReturnUser_WhenUserIdIsFound(){
            //Arrange
            ID id = ID.of(USER_ID);
            UserGetByIdCommand command = new UserGetByIdCommand(USER_ID);

            when(userRepository.getUserById(id)).thenReturn(Optional.of(user));
            when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

            //Act
            UserDTO result = userService.getUserById(command);

            //Assert
            assertEquals(userDTO,result);

            verify(userRepository).getUserById(id);
            verify(userMapper).toDTO(any(User.class));
        }
        @Test
        public void getUserByID_ShouldThrowUserNotFoundException_WhenUserIdIsNotFound(){
            //Arrange
            UserGetByIdCommand command = new UserGetByIdCommand(USER_ID_INVALID);
            String messageExpected = "User with ID: " +USER_ID_INVALID+ " not found.";

            when(userRepository.getUserById(ID.of(USER_ID_INVALID))).thenReturn(Optional.empty());

            //Act
            UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                    ()-> userService.getUserById(command));

            //Assert
            assertEquals(messageExpected,exception.getMessage());

            verify(userRepository).getUserById(ID.of(USER_ID_INVALID));
        }
    }

    @Nested
    @DisplayName("GetUserByEmail Tests")
    class GetUserByEmailTests{
        @Test
        public void getUserByEmail_ShouldReturnUser_WhenUserEmailIsFound(){
            //Arrange
            Email email = new Email(USER_EMAIL);
            UserGetByEmailCommand command = new UserGetByEmailCommand(USER_EMAIL);

            when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));
            when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

            //Act
            UserDTO result = userService.getUserByEmail(command);

            //Assert
            assertEquals(userDTO,result);

            verify(userRepository).getUserByEmail(email);
            verify(userMapper).toDTO(any(User.class));
        }
        @Test
        public void getUserByEmail_ShouldThrowUserNotFoundException_WhenEmailIsNotFound(){
            //Arrange
            Email email = new Email("email_invalid@gmail.es");
            UserGetByEmailCommand command = new UserGetByEmailCommand(email.email());
            String messageExpected = "User with email: " +email.email()+ " not found.";

            when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());

            //Act
            UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                    ()-> userService.getUserByEmail(command));

            //Assert
            assertEquals(messageExpected,exception.getMessage());

            verify(userRepository).getUserByEmail(email);
        }
    }

    @Nested
    @DisplayName("GetAllUsers Tests")
    class GetAllUserTests{
        @Test
        public void getAllUsers_ShouldReturnUserList_WhenIsOK(){
            //Arrange
            List<User> userList = new ArrayList<>();
            userList.add(user);

            List<UserDTO> userDTOS = new ArrayList<>();
            userDTOS.add(userDTO);

            when(userRepository.getAllUsers()).thenReturn(userList);
            when(userMapper.toListDTO(userList)).thenReturn(userDTOS);

            //Act
            List<UserDTO> result = userService.getAllUsers();

            //Assert
            assertEquals(userDTOS,result);

            verify(userRepository).getAllUsers();
            verify(userMapper).toListDTO(userList);
        }
    }

    @Nested
    @DisplayName("UpdateUser Tests")
    class UpdateUserTests{
        @Test
        public void updateUser_ShouldUpdateUser_WhenUserIdIsFound(){
            //Arrange
            ID id = ID.of(USER_ID);
            UserDTO userDtoUpdate = new UserDTO();
            String newLastName = "Smith";
            userDtoUpdate.setLastName(newLastName);
            UserUpdateCommand command = new UserUpdateCommand(USER_ID,userDtoUpdate);

            when(userRepository.getUserById(id)).thenReturn(Optional.of(user));
            doNothing().when(userRepository).updateUser(user);

            //Act
            userService.updateUser(command);

            //Assert
            verify(userRepository).getUserById(id);

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).updateUser(userCaptor.capture());

            User updatedUser = userCaptor.getValue();
            assertEquals(newLastName, updatedUser.getFullName().lastName());
        }
        @Test
        public void updateUser_ShouldThrowUserNotFoundException_WhenUserIdIsNotFound(){
            //Arrange
            ID invalidID = ID.of(USER_ID_INVALID);
            UserUpdateCommand command = new UserUpdateCommand(USER_ID_INVALID,userDTO);
            String messageExpected = "User with ID: " +USER_ID_INVALID+ " not found.";

            when(userRepository.getUserById(invalidID)).thenReturn(Optional.empty());

            //Act
            UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                    ()-> userService.updateUser(command));

            //Assert
            assertEquals(messageExpected,exception.getMessage());

            verify(userRepository).getUserById(invalidID);
        }
    }

    @Nested
    @DisplayName("UpdatePassword Tests")
    class UpdatePasswordTests{
        @Test
        public void updatePassword_ShouldUpdateUser_WhenUserIdIsFound(){
            //Arrange
            ID id = ID.of(USER_ID);
            String newPassword = "newPassword123$";
            UserUpdatePasswordCommand command = new UserUpdatePasswordCommand(USER_ID,USER_PASSWORD,newPassword);

            when(userRepository.getUserById(id)).thenReturn(Optional.of(user));
            doNothing().when(userRepository).updatePassword(user);

            //Act
            userService.updatePassword(command);

            //Assert
            verify(userRepository).getUserById(id);

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).updatePassword(userCaptor.capture());

            User updatedUser = userCaptor.getValue();
            assertTrue(updatedUser.getPassword().matchesPassword(newPassword));
        }
        @Test
        public void updatePassword_ShouldThrowUserNotFoundException_WhenUserIdIsNotFound(){
            //Arrange
            ID invalidID = ID.of(USER_ID_INVALID);
            String newPassword = "newPassword123$";
            UserUpdatePasswordCommand command = new UserUpdatePasswordCommand(USER_ID_INVALID,USER_PASSWORD,newPassword);
            String messageExpected = "User with ID: " +USER_ID_INVALID+ " not found.";

            when(userRepository.getUserById(invalidID)).thenReturn(Optional.empty());

            //Act
            UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                    ()-> userService.updatePassword(command));

            //Assert
            assertEquals(messageExpected,exception.getMessage());

            verify(userRepository).getUserById(invalidID);
        }
    }

    @Nested
    @DisplayName("DeleteUser Tests")
    class DeleteUserTests{
        @Test
        public void deleteUser_ShouldDeleteUser_WhenUserIsFound(){
            //Arrange
            ID id = ID.of(USER_ID);
            UserDeleteCommand command = new UserDeleteCommand(USER_ID);

            when(userRepository.getUserById(id)).thenReturn(Optional.of(user));
            doNothing().when(userRepository).deleteUser(id);

            //Act
            userService.deleteUser(command);

            //Assert
            verify(userRepository).getUserById(id);

            ArgumentCaptor<ID> idCaptor = ArgumentCaptor.forClass(ID.class);
            verify(userRepository).deleteUser(idCaptor.capture());

            ID deleteId = idCaptor.getValue();
            assertEquals(id,deleteId);
        }
        @Test
        public void deleteUser_ShouldThrowUserNotFoundException_WhenUserIdNotFound(){
            //Arrange
            ID invalidID = ID.of(USER_ID_INVALID);
            UserDeleteCommand command = new UserDeleteCommand(USER_ID_INVALID);
            String messageExpected = "User with ID: " +USER_ID_INVALID+ " not found.";

            when(userRepository.getUserById(invalidID)).thenReturn(Optional.empty());

            //Act
            UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                    ()-> userService.deleteUser(command));

            //Assert
            assertEquals(messageExpected,exception.getMessage());

            verify(userRepository).getUserById(invalidID);
        }
        @Test
        public void deleteUser_ShouldThrowOperationNotAuthorizedByRoleException_WhenUserIsNotAdmin(){
            //Arrange
            ID id = ID.of(USER_ID);
            user.setRole(Role.USER);
            UserDeleteCommand command = new UserDeleteCommand(USER_ID);
            String messageExpected = "You cannot delete a User because you are not Admin";

            when(userRepository.getUserById(id)).thenReturn(Optional.of(user));

            //Act
            OperationNotAuthorizedByRoleException exception = assertThrows(OperationNotAuthorizedByRoleException.class,
                    ()-> userService.deleteUser(command));

            //Assert
            assertEquals(messageExpected,exception.getMessage());

            verify(userRepository).getUserById(id);
        }
    }

}