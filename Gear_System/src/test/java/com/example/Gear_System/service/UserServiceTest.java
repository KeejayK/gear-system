package com.example.Gear_System.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;



import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.springframework.security.crypto.password.PasswordEncoder;



import com.example.Gear_System.model.User;

import com.example.Gear_System.repository.UserRepository;

import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    private User user;

    @BeforeEach
    public void setup() {

        user = new User("test", "password", "test@testing.com", false, false, LocalDate.now(), null);
        lenient().when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
    }



    @Test
    public void testRegisterUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("encryptedPassword", registeredUser.getPassword());
        assertFalse(registeredUser.isOfficer());
        assertFalse(registeredUser.isVerified());

    }


    @Test
    public void testVerifyUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        boolean isVerified = userService.verifyUserByEmail(user.getEmail());

        assertTrue(isVerified);

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertTrue(savedUser.isVerified());

    }

}
