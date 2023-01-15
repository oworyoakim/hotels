package com.hotels.properties.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class PropertyAmenity {
    Long id;
    Long propertyId;
    Long amenityId;
    LocalDateTime createdAt;
}
