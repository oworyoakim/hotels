package com.hotels.users.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Permission {
    Long id;
    String name;
    String slug;
    String description;
    LocalDateTime createdAt;
}
