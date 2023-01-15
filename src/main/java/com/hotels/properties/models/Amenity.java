package com.hotels.properties.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Amenity {
    Long id;
    String name;
    String avatar;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
