package com.hotels.properties.requests;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAmenityRequest {
    @NotBlank(message = "Name of amenity is required")
    String name;

    @NotBlank(message = "Type of amenity is required")
    String amenityType;
    String avatar;
}
