package com.almaraz_john.user_service.application.service;

import com.almaraz_john.user_service.application.command.UserCreateCommand;
import com.almaraz_john.user_service.application.command.UserDeleteCommand;
import com.almaraz_john.user_service.application.command.UserGetByEmailCommand;
import com.almaraz_john.user_service.application.command.UserGetByIdCommand;
import com.almaraz_john.user_service.application.command.UserUpdateCommand;
import com.almaraz_john.user_service.application.command.UserUpdatePasswordCommand;
import com.almaraz_john.user_service.application.dto.UserDTO;
import com.almaraz_john.user_service.application.mapper.UserMapper;
import com.almaraz_john.user_service.application.port.input.UserService;
import com.almaraz_john.user_service.application.port.output.UserRepository;
import com.almaraz_john.user_service.domain.aggregate.User;
import com.almaraz_john.user_service.domain.enums.Role;
import com.almaraz_john.user_service.domain.exception.OperationNotAuthorizedByRoleException;
import com.almaraz_john.user_service.domain.exception.UserNotFoundException;
import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.FullName;
import com.almaraz_john.user_service.domain.valueObjects.ID;
import com.almaraz_john.user_service.domain.valueObjects.Password;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDTO create(UserCreateCommand command) {
        UserDTO userDTO = command.getUserDTO();
        User user = new User(
                ID.create(),
                new FullName(userDTO.getFirstName(), userDTO.getLastName()),
                new Email(userDTO.getEmail()),
                new Password(userDTO.getPassword()),
                userDTO.getRole()
        );

        return mapper.toDTO(repository.create(user));
    }

    @Override
    public UserDTO getUserById(UserGetByIdCommand command) {
        ID id = ID.of(command.getId());

        User user = repository.getUserById(id).orElseThrow(
                ()-> new UserNotFoundException("User with ID: " + id.getValue() + " not found.")
        );

        return mapper.toDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(UserGetByEmailCommand command) {
        Email email = new Email(command.getEmail());

        User user = repository.getUserByEmail(email).orElseThrow(
                ()-> new UserNotFoundException("User with email: " + email.email() + " not found.")
        );

        return mapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapper.toListDTO(repository.getAllUsers());
    }

    @Override
    public void updateUser(UserUpdateCommand command) {
        ID id = ID.of(command.getId());
        UserDTO userDTO = command.getUserDTO();

        User user = repository.getUserById(id).orElseThrow(
                ()-> new UserNotFoundException("User with ID: " + id.getValue() + " not found.")
        );

        user.updateUserProfile(userDTO);

        repository.updateUser(user);
    }

    @Override
    public void updatePassword(UserUpdatePasswordCommand command) {
        ID id = ID.of(command.getUserId());
        String currentPassword = command.getCurrentPassword();
        String newPassword = command.getNewPassword();

        User user = repository.getUserById(id).orElseThrow(
                ()-> new UserNotFoundException("User with ID: " + id.getValue() + " not found.")
        );
        user.updatePassword(currentPassword,newPassword);

        repository.updatePassword(user);
    }

    @Override
    public void deleteUser(UserDeleteCommand command) {
        ID id = ID.of(command.getId());

        User user = repository.getUserById(id).orElseThrow(
                ()-> new UserNotFoundException("User with ID: " + id.getValue() + " not found.")
        );

        if (user.getRole() != Role.ADMIN){
            throw new OperationNotAuthorizedByRoleException("You cannot delete a User because you are not Admin");
        }

        repository.deleteUser(id);
    }
}
