package com.almaraz_john.user_service.infrastructure.adapters.input.controller;

import com.almaraz_john.user_service.application.command.UserCreateCommand;
import com.almaraz_john.user_service.application.command.UserDeleteCommand;
import com.almaraz_john.user_service.application.command.UserGetByEmailCommand;
import com.almaraz_john.user_service.application.command.UserGetByIdCommand;
import com.almaraz_john.user_service.application.command.UserUpdateCommand;
import com.almaraz_john.user_service.application.command.UserUpdatePasswordCommand;
import com.almaraz_john.user_service.application.dto.UserDTO;
import com.almaraz_john.user_service.application.port.input.UserService;
import com.almaraz_john.user_service.domain.exception.InvalidCommandException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateCommand command){
        return new ResponseEntity<>(userService.create(command), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO>getUserByID(@PathVariable UUID userId){
        UserGetByIdCommand command = new UserGetByIdCommand(userId);

        return ResponseEntity.ok(userService.getUserById(command));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO>getUserByEmail(@PathVariable String email){
        UserGetByEmailCommand command = new UserGetByEmailCommand(email);

        return ResponseEntity.ok(userService.getUserByEmail(command));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>>getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void>updateUser(@PathVariable UUID userId, @RequestBody UserDTO userDTO){
        UserUpdateCommand command = new UserUpdateCommand(userId,userDTO);
        userService.updateUser(command);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Void>updatePassword(@PathVariable UUID userId, @RequestBody UserUpdatePasswordCommand command){
        if (!userId.equals(command.getUserId())) {
            throw new InvalidCommandException("userId in path is distinct to userId in command");
        }
        userService.updatePassword(command);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void>deleteUser(@PathVariable UUID userId){
        UserDeleteCommand command = new UserDeleteCommand(userId);
        userService.deleteUser(command);

        return ResponseEntity.noContent().build();
    }
}
