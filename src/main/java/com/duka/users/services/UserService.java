package com.duka.users.services;

import com.duka.users.models.User;

import java.util.Optional;

public interface UserService {

    Optional<User> find(Long id);

    Optional<User> findByEmailAddress(String email);

    Integer updateToken(Long id, String token);
}
