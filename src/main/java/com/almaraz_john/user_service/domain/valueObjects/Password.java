package com.almaraz_john.user_service.domain.valueObjects;

import com.almaraz_john.user_service.domain.exception.InvalidPasswordFormatException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@ToString(exclude = "passwordHash")
public final class Password {


    private final String passwordHash;
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    public Password(String rawPassword) {
        if (!isValidPassword(rawPassword)) {
            throw new InvalidPasswordFormatException("Invalid password. It must be at least 8 characters, " +
                    "one uppercase letter, one lowercase letter, one number and one special character.");
        }

        this.passwordHash = encryptPassword(rawPassword);
    }

    private Password(String passwordHash, boolean alreadyHashed) {
        this.passwordHash = passwordHash;
    }

    public static Password ofHash(String hash) {
        if(hash == null || hash.isBlank()){
            throw new IllegalArgumentException("Hash code cannot be null or empty");
        }else{
            return new Password(hash, true);
        }
    }

    private boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    private String encryptPassword(String rawPassword) {
        return PASSWORD_ENCODER.encode(rawPassword);
    }

    public boolean matchesPassword(String rawPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, this.passwordHash);
    }

}
