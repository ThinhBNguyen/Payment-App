package com.example.Revagenda.Rev_Pay.service;


import com.example.Revagenda.Rev_Pay.dao.UserRepository;
import com.example.Revagenda.Rev_Pay.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private PasswordEncoder mockPasswordEncoder;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setUserName("johnDoe");
        testUser.setEmail("john.doe@example.com");
        testUser.setPassWord(mockPasswordEncoder.encode("password"));
    }

    @Test
    public void test_createUserExpectNewUser() {
        when(mockUserRepository.findByUserName(testUser.getUserName())).thenReturn(Optional.empty());
        when(mockUserRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode(testUser.getPassWord())).thenReturn("encodedPassword");
        when(mockUserRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User savedUser = userService.createUser(new User(testUser.getId(), testUser.getFirstName(), testUser.getLastName(), testUser.getUserName(), testUser.getEmail(), testUser.getPassWord()));
        // Assert
        assertNotNull(savedUser);
        assertEquals(testUser, savedUser);
    }


    @Test
    public void test_getAllUserExpectAllUser() {
        User user1 = new User(1, "user1", "user1", "username1", "password", "user1@example.com");
        User user2 = new User(2, "user2", "user2", "username2", "password2", "user2@example.com");
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(mockUserRepository.findAll()).thenReturn(expectedUsers);

        List<User> testUsers = userService.getAllUser();

        assertEquals(expectedUsers, testUsers);

    }

    @Test
    public void test_findUserByUsernameExpectUserWithUsername() {
        String username = "johnDoe";
        User testUser = new User();
        testUser.setUserName(username);
        when(mockUserRepository.findByUserName(username)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findByUserName(username);

        assertNotNull(foundUser);
        assertEquals(testUser, foundUser);
    }

    @Test
    public void test_findUserByUsernameExpectUserNotFound() {

        String username = "user";
        when(mockUserRepository.findByUserName(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.findByUserName(username);
        });
    }

    @Test
    public void test_findUserByUsernameOrEmailExpectUserWithUsernameOrEmail() {
        String recipientIdentifier = testUser.getEmail();
        User expectedUser = new User();
        expectedUser.setUserName(recipientIdentifier);

        when(mockUserRepository.findByUserNameOrEmail(recipientIdentifier)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.findByUsernameOrEmail(recipientIdentifier);

        assertEquals(expectedUser, actualUser);
    }

}
