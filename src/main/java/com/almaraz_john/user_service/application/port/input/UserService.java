package com.almaraz_john.user_service.application.port.input;

import com.almaraz_john.user_service.application.command.UserCreateCommand;
import com.almaraz_john.user_service.application.command.UserDeleteCommand;
import com.almaraz_john.user_service.application.command.UserGetByEmailCommand;
import com.almaraz_john.user_service.application.command.UserGetByIdCommand;
import com.almaraz_john.user_service.application.command.UserUpdateCommand;
import com.almaraz_john.user_service.application.command.UserUpdatePasswordCommand;
import com.almaraz_john.user_service.application.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(UserCreateCommand command);
    UserDTO getUserById(UserGetByIdCommand command);
    UserDTO getUserByEmail(UserGetByEmailCommand command);
    List<UserDTO> getAllUsers();
    void updateUser(UserUpdateCommand command);
    void updatePassword(UserUpdatePasswordCommand command);
    void deleteUser(UserDeleteCommand command);
}
