package com.hotels.users.services;

import com.hotels.users.models.User;

import java.util.Optional;

public interface UserService {
    static final String TEST_EMAIL = "test@hotels.test";
    static final String TEST_PASSWORD = "password";
    Optional<User> find(Long id);
    Optional<User> findByEmailAddress(String email);
    Integer updateToken(Long id, String token);
    Optional<User> create(User user);
    String generatePassword();
    String generatePassword(int length);
}
