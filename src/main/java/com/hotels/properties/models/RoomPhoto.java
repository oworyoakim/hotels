package com.hotels.properties.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class RoomPhoto {
    Long id;
    Long roomId;
    Integer width;
    Integer height;
    String previewUrl;
    String mainUrl;
    Boolean coverPhoto;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
