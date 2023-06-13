package com.gride29.airbnb.clone.backend.user.domain;

import com.gride29.airbnb.clone.backend.TestConfig;
import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.models.User;
import com.gride29.airbnb.clone.backend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes = TestConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Clear all users before each test
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnUserByUsername() {
        User user = new User("JohnDoe", "john@example.com", "password123");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("JohnDoe");

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals("JohnDoe", foundUser.get().getUsername());
        Assertions.assertEquals("john@example.com", foundUser.get().getEmail());
        Assertions.assertEquals("password123", foundUser.get().getPassword());
    }

    @Test
    public void shouldCheckUsernameExists() {
        User user = new User("JohnDoe", "john@example.com", "password123");
        userRepository.save(user);

        boolean usernameExists = userRepository.existsByUsername("JohnDoe");
        boolean nonExistingUsernameExists = userRepository.existsByUsername("NonExistingUser");

        Assertions.assertTrue(usernameExists);
        Assertions.assertFalse(nonExistingUsernameExists);
    }

    @Test
    public void shouldCheckEmailExists() {
        User user = new User("JohnDoe", "john@example.com", "password123");
        userRepository.save(user);

        boolean emailExists = userRepository.existsByEmail("john@example.com");
        boolean nonExistingEmailExists = userRepository.existsByEmail("nonexisting@example.com");

        Assertions.assertTrue(emailExists);
        Assertions.assertFalse(nonExistingEmailExists);
    }

    private List<Reservation> createTestUsers() {
        LocalDateTime createdAt = LocalDateTime.now();
        Reservation reservation1 = new Reservation(
                "User1",
                "Listing1",
                LocalDateTime.of(2023, 5, 1, 10, 0),
                LocalDateTime.of(2023, 5, 3, 14, 0),
                200,
                createdAt
        );
        Reservation reservation2 = new Reservation(
                "User2",
                "Listing2",
                LocalDateTime.of(2023, 6, 10, 12, 0),
                LocalDateTime.of(2023, 6, 15, 10, 0),
                500,
                createdAt
        );

        return Arrays.asList(reservation1, reservation2);
    }
}

