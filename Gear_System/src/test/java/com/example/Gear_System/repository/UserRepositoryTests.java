package com.example.Gear_System.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Gear_System.model.User;

import static org.assertj.core.api.Assertions.assertThat;




@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByEmail_thenReturnUser() {
        User john = new User("John Doe", "john@example.com", "password123", false, false, null, null);
        userRepository.save(john);

        Optional<User> found = userRepository.findByEmail(john.getEmail());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(john.getEmail());
    }
}   
