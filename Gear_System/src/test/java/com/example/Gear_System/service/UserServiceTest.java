package com.example.Gear_System.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Gear_System.model.User;
import com.example.Gear_System.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

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

    @BeforeEach
    public void setup() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("officer@example.com", "password", 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_OFFICER")));
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        
        lenient().when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
    }

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testVerifyUserByEmail() {
        User user = new User("test", "password", "test@testing.com", false, false, LocalDate.now(), null);

        when(userRepository.findByEmail("test@testing.com")).thenReturn(Optional.of(user));

        boolean isVerified = userService.verifyUserByEmail("test@testing.com");

        assertTrue(isVerified);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertTrue(savedUser.isVerified());
    }

}
