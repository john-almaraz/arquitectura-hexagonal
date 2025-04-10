package com.almaraz_john.user_service.application.dto;

import com.almaraz_john.user_service.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object representing a user")
public class UserDTO {

    @Schema(description = "Unique identifier of the user", example = "c13d4c38-90eb-4320-8d3a-1fc1bbf5fc7e")
    private UUID id;

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "User password (hashed or plain depending on context)", example = "Secret123$", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Schema(description = "User role in the system", example = "ADMIN or USER")
    private Role role;
}
