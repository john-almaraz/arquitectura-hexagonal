package com.almaraz_john.user_service.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Command to get User by Email")
public class UserGetByEmailCommand {

    @Schema(description = "Email to get a user")
    private String email;
}
