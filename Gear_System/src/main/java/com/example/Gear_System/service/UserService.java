package com.example.Gear_System.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Gear_System.model.User;
import com.example.Gear_System.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register user
    public User registerUser(User user) {
        // Does user with email exist?
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already in use.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOfficer(false);
        user.setVerified(false);
        return userRepository.save(user);
    }

    // Verify user by email
    public boolean verifyUserByEmail(String email) {
        if (!isCurrentUserOfficer()) {
            throw new IllegalStateException("Only officers can verify users.");
        }

        User user = userRepository.findByEmail(email)
                   .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (user.isVerified()) {
            throw new IllegalStateException("User is already verified.");
        }

        user.setVerified(true);
        userRepository.save(user);
        return true;
    }

    private boolean isCurrentUserOfficer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
               .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_OFFICER"));
    }

    // Find user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return true;
        }
        return false;
    }


}