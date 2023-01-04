package com.hotels.users.models;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Value
public class User {
    Long id;

    UserType type;

    String name;

    String email;

    String password;

    Long clientId;

    Boolean active;

    String token;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
