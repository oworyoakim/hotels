package com.hotels.properties.models;

import java.time.LocalDateTime;

import com.hotels.properties.enumerations.AmenityType;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Amenity {
    Long id;
    String name;
    AmenityType amenityType;
    String avatar;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
