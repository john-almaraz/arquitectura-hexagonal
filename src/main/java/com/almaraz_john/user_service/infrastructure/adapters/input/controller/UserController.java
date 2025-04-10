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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "User", description = "CRUD operations for managing users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateCommand command){
        return new ResponseEntity<>(userService.create(command), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO>getUserByID(@PathVariable UUID userId){
        UserGetByIdCommand command = new UserGetByIdCommand(userId);

        return ResponseEntity.ok(userService.getUserById(command));
    }

    @Operation(summary = "Get a user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO>getUserByEmail(@PathVariable String email){
        UserGetByEmailCommand command = new UserGetByEmailCommand(email);

        return ResponseEntity.ok(userService.getUserByEmail(command));
    }

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    @GetMapping
    public ResponseEntity<List<UserDTO>>getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Update a user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<Void>updateUser(@PathVariable UUID userId, @RequestBody UserDTO userDTO){
        UserUpdateCommand command = new UserUpdateCommand(userId,userDTO);
        userService.updateUser(command);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid command or userId mismatch")
    })
    @PutMapping("/{userId}/password")
    public ResponseEntity<Void>updatePassword(@PathVariable UUID userId, @RequestBody UserUpdatePasswordCommand command){
        if (!userId.equals(command.getUserId())) {
            throw new InvalidCommandException("userId in path is distinct to userId in command");
        }
        userService.updatePassword(command);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void>deleteUser(@PathVariable UUID userId){
        UserDeleteCommand command = new UserDeleteCommand(userId);
        userService.deleteUser(command);

        return ResponseEntity.noContent().build();
    }
}
