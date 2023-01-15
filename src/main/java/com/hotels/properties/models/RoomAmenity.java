package com.hotels.properties.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class RoomAmenity {
    Long id;
    Long roomId;
    Long amenityId;
    LocalDateTime createdAt;
}
