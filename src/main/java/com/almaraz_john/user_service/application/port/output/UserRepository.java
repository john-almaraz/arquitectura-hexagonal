package com.almaraz_john.user_service.application.port.output;

import com.almaraz_john.user_service.domain.aggregate.User;
import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.ID;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User create(User user);
    Optional<User> getUserById(ID userId);
    Optional<User> getUserByEmail(Email email);
    List<User> getAllUsers();
    void updateUser(User user);
    void updatePassword(User user);
    void deleteUser(ID userId);
}
