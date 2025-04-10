package com.almaraz_john.user_service.application.command;

import com.almaraz_john.user_service.application.dto.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Command to create a new user")
public class UserCreateCommand {

    @Schema(description = "User data for creation")
    private UserDTO userDTO;
}
