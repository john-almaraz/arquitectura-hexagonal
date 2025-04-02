package com.almaraz_john.user_service.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserGetByEmailCommand {
    private String email;
}
