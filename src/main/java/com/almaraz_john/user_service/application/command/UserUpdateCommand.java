package com.almaraz_john.user_service.application.command;

import com.almaraz_john.user_service.application.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserUpdateCommand {
    private UUID id;
    private UserDTO userDTO;
}
