package com.hotels.properties.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Room {
    Long id;
    String name;
    Long propertyId;
    String description;
    Double nightlyRate;
    Boolean published;
    Integer bathrooms;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
