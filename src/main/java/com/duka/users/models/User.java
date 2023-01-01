package com.duka.users.models;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Value
public class User {
    Long id;

    String name;

    String email;

    String password;

    Long clientId;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
