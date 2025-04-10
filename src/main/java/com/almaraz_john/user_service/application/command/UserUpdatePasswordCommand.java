package com.almaraz_john.user_service.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Command to update user password")
public class UserUpdatePasswordCommand {

    @Schema(description = "User ID")
    private UUID userId;

    @Schema(description = "Current password")
    private String currentPassword;

    @Schema(description = "New Password")
    private String newPassword;
}
