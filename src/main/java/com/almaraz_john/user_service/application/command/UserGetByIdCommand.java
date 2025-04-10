package com.almaraz_john.user_service.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Command to get User by ID")
public class UserGetByIdCommand {

    @Schema(description = "ID to get User")
    private UUID id;
}
