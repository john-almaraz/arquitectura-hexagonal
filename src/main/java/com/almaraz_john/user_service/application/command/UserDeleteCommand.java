package com.almaraz_john.user_service.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDeleteCommand {
    private UUID id;
}
