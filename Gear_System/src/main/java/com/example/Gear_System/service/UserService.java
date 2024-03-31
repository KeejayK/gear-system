package com.example.Gear_System.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Gear_System.model.User;
import com.example.Gear_System.repository.UserRepository;

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
        // Encrypt password NOTE FOR FUTURE KEEJAY: CHECK THAT THIS WORKS 
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOfficer(false);
        user.setVerified(false);
        return userRepository.save(user);
    }

    // Verify user by email
    public boolean verifyUserByEmail(String email) {
        // Must update this so only officers may do this
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Find user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}