package com.almaraz_john.user_service.infrastructure.adapters.output.persistence.repository;

import com.almaraz_john.user_service.application.port.output.UserRepository;
import com.almaraz_john.user_service.domain.aggregate.User;
import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.ID;
import com.almaraz_john.user_service.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.almaraz_john.user_service.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJPA repositoryJPA;
    private final UserPersistenceMapper mapper;

    @Override
    public User create(User user) {
         UserEntity userEntity = mapper.toEntity(user);

        return mapper.toDomain(repositoryJPA.save(userEntity));
    }

    @Override
    public Optional<User> getUserById(ID userId) {
        Optional<UserEntity> userEntity = repositoryJPA.findById(userId.getValue());

        return userEntity.map(mapper::toDomain);
    }

    @Override
    public Optional<User> getUserByEmail(Email email) {
        Optional<UserEntity> userEntity = repositoryJPA.findUserByEmail(email.email());

        return userEntity.map(mapper::toDomain);
    }

    @Override
    public List<User> getAllUsers() {
        return mapper.toDomainList(repositoryJPA.findAll());
    }

    @Override
    public void updateUser(User user) {
        repositoryJPA.save(mapper.toEntity(user));
    }

    @Override
    public void updatePassword(User user) {
        repositoryJPA.save(mapper.toEntity(user));
    }

    @Override
    public void deleteUser(ID userId) {
        repositoryJPA.deleteById(userId.getValue());
    }
}
