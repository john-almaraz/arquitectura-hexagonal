package com.almaraz_john.user_service.application.command;

import com.almaraz_john.user_service.application.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateCommand {
    private UserDTO userDTO;
}
