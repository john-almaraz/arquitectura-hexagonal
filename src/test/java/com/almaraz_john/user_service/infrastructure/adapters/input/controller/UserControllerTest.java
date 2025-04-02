package com.almaraz_john.user_service.infrastructure.adapters.input.controller;

import com.almaraz_john.user_service.application.command.UserCreateCommand;
import com.almaraz_john.user_service.application.command.UserDeleteCommand;
import com.almaraz_john.user_service.application.command.UserGetByEmailCommand;
import com.almaraz_john.user_service.application.command.UserGetByIdCommand;
import com.almaraz_john.user_service.application.command.UserUpdateCommand;
import com.almaraz_john.user_service.application.command.UserUpdatePasswordCommand;
import com.almaraz_john.user_service.application.dto.UserDTO;
import com.almaraz_john.user_service.application.port.input.UserService;

import com.almaraz_john.user_service.domain.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    //COMMON VARIABLES
    private static final UUID USER_ID = UUID.fromString("37e3ca99-fe84-4959-92d8-4f25fa73e65f");
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Almaraz";
    private static final String USER_EMAIL = "john_almaraz123@gmail.es";
    private static final String USER_PASSWORD = "JohnAlmaraz$123";
    private UserDTO userDTO;

    @BeforeEach
    void setup(){
        userDTO = new UserDTO(
                USER_ID,
                USER_FIRST_NAME,
                USER_LAST_NAME,
                USER_EMAIL,
                USER_PASSWORD,
                Role.ADMIN
        );
    }

    @Test
    void testCreateUser() throws Exception {
        UserCreateCommand command = new UserCreateCommand(userDTO);

        when(userService.create(any(UserCreateCommand.class))).thenReturn(userDTO);

        String commandJson = mapper.writeValueAsString(command);
        String expectedJson = mapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(any(UserGetByIdCommand.class))).thenReturn(userDTO);

        String expectedJson = mapper.writeValueAsString(userDTO);

        mockMvc.perform(get("/users/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        when(userService.getUserByEmail(any(UserGetByEmailCommand.class))).thenReturn(userDTO);

        String expectedJson = mapper.writeValueAsString(userDTO);

        mockMvc.perform(get("/users/email/{email}", USER_EMAIL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDTO));

        String expectedJson = mapper.writeValueAsString(Collections.singletonList(userDTO));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testUpdateUser() throws Exception {
        String userDTOJson = mapper.writeValueAsString(userDTO);

        mockMvc.perform(put("/users/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).updateUser(any(UserUpdateCommand.class));
    }

    @Test
    void testUpdatePassword() throws Exception {
        UserUpdatePasswordCommand command = new UserUpdatePasswordCommand(USER_ID, USER_PASSWORD, "NewPass1$");
        String commandJson = mapper.writeValueAsString(command);

        mockMvc.perform(put("/users/{userId}/password", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).updatePassword(any(UserUpdatePasswordCommand.class));
    }

    @Test
    void testUpdatePasswordUserIdMismatch() throws Exception {
        UUID pathUserId = UUID.randomUUID();
        UserUpdatePasswordCommand command = new UserUpdatePasswordCommand(USER_ID, USER_PASSWORD, "NewPass1$");
        String commandJson = mapper.writeValueAsString(command);

        mockMvc.perform(put("/users/{userId}/password", pathUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{userId}", USER_ID))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).deleteUser(any(UserDeleteCommand.class));
    }
}
