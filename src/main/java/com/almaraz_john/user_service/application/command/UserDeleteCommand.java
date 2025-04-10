package com.almaraz_john.user_service.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Command to delete a user")
public class UserDeleteCommand {

    @Schema(description = "User ID to delete a user")
    private UUID id;
}
