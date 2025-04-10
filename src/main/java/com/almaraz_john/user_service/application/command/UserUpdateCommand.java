package com.almaraz_john.user_service.application.command;

import com.almaraz_john.user_service.application.dto.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Command to get update a User")
public class UserUpdateCommand {

    @Schema(description = "User ID that we will update")
    private UUID id;

    @Schema(description = "New user data")
    private UserDTO userDTO;
}
