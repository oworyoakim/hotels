package com.hotels.auth.models;

import com.hotels.users.enumerations.UserType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Value
public class ProfileResponse {
    Long id;
    UserType type;
    String name;
    String email;
    Long clientId;
    Boolean active;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
