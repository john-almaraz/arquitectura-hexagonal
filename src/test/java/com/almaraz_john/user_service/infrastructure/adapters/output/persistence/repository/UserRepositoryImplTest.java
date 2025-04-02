package com.almaraz_john.user_service.infrastructure.adapters.output.persistence.repository;

import com.almaraz_john.user_service.domain.aggregate.User;
import com.almaraz_john.user_service.domain.enums.Role;
import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.FullName;
import com.almaraz_john.user_service.domain.valueObjects.ID;
import com.almaraz_john.user_service.domain.valueObjects.Password;
import com.almaraz_john.user_service.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.almaraz_john.user_service.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserRepositoryJPA repositoryJPA;

    @Mock
    private UserPersistenceMapper mapper;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    //COMMON VARIABLES
    private static final UUID USER_ID = UUID.fromString("37e3ca99-fe84-4959-92d8-4f25fa73e65f");
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Almaraz";
    private static final String USER_EMAIL = "john_almaraz123@gmail.es";
    private static final String USER_PASSWORD = "JohnAlmaraz$123";
    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setup(){
        user = new User(
                ID.of(USER_ID),
                new FullName(USER_FIRST_NAME,USER_LAST_NAME),
                new Email(USER_EMAIL),
                new Password(USER_PASSWORD),
                Role.ADMIN
        );

        userEntity = new UserEntity(
                USER_ID,
                USER_FIRST_NAME,
                USER_LAST_NAME,
                USER_EMAIL,
                USER_PASSWORD,
                Role.ADMIN
        );
    }

    @Test
    public void createUser_ShouldCreateUser_WhenUserIsOK(){
        // Arrange
        when(mapper.toEntity(any(User.class))).thenReturn(userEntity);
        when(repositoryJPA.save(any(UserEntity.class))).thenReturn(userEntity);
        when(mapper.toDomain(any(UserEntity.class))).thenReturn(user);

        // Act
        User result = userRepository.create(user);

        // Assert
        assertEquals(user,result);

        verify(mapper).toEntity(any(User.class));
        verify(repositoryJPA).save(any(UserEntity.class));
        verify(mapper).toDomain(any(UserEntity.class));
    }

    @Test
    public void getUserByID_ShouldReturnUser_WhenUserIdIsFound(){
        // Arrange
        when(repositoryJPA.findById(USER_ID)).thenReturn(Optional.of(userEntity));
        when(mapper.toDomain(any(UserEntity.class))).thenReturn(user);

        // Act
        Optional<User> result = userRepository.getUserById(ID.of(USER_ID));

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user,result.get());

        verify(repositoryJPA).findById(USER_ID);
        verify(mapper).toDomain(any(UserEntity.class));
    }

    @Test
    public void getUserByEmail_ShouldReturnUser_WhenEmailIsFound(){
        // Arrange
        when(repositoryJPA.findUserByEmail(USER_EMAIL)).thenReturn(Optional.of(userEntity));
        when(mapper.toDomain(any(UserEntity.class))).thenReturn(user);

        // Act
        Optional<User> result = userRepository.getUserByEmail(new Email(USER_EMAIL));

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user,result.get());

        verify(repositoryJPA).findUserByEmail(USER_EMAIL);
        verify(mapper).toDomain(any(UserEntity.class));
    }

    @Test
    public void getAllUsers_ShouldReturnUserList_WhenIsOk(){
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(user);

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity);

        when(repositoryJPA.findAll()).thenReturn(userEntities);
        when(mapper.toDomainList(anyList())).thenReturn(userList);

        // Act
        List<User> result = userRepository.getAllUsers();

        // Assert
        assertEquals(userList,result);

        verify(repositoryJPA).findAll();
        verify(mapper).toDomainList(anyList());
    }

    @Test
    public void updateUser_ShouldUpdateUser_WhenUserIsOK(){
        // Arrange
        when(mapper.toEntity(any(User.class))).thenReturn(userEntity);
        when(repositoryJPA.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        userRepository.updateUser(user);

        // Verify
        verify(mapper).toEntity(any(User.class));
        verify(repositoryJPA).save(any(UserEntity.class));
    }

    @Test
    public void updatePassword_ShouldUpdatePassword_WhenUserIsOK(){
        // Arrange
        when(mapper.toEntity(any(User.class))).thenReturn(userEntity);
        when(repositoryJPA.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        userRepository.updatePassword(user);

        // Verify
        verify(mapper).toEntity(any(User.class));
        verify(repositoryJPA).save(any(UserEntity.class));
    }

    @Test
    public void deleteUser_ShouldDeleteUser_WhenUserIdIsOK(){
        // Arrange
        doNothing().when(repositoryJPA).deleteById(USER_ID);

        // Act
        userRepository.deleteUser(ID.of(USER_ID));

        // Verify
        verify(repositoryJPA).deleteById(USER_ID);
    }
}