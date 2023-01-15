package com.hotels.properties.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class PropertyPhoto {
    Long id;
    Long propertyId;
    Integer width;
    Integer height;
    String previewUrl;
    String mainUrl;
    Boolean coverPhoto;
    LocalDateTime createdAt;
}
