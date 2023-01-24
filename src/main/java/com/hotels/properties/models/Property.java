package com.hotels.properties.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.hotels.properties.enumerations.PropertyType;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Property {
    Long id;
    Long clientId;
    Long userId;
    String name;
    String description;
    PropertyType propertyType;
    String listingCurrency;
    Double nightlyRate;
    Integer bathrooms;
    Boolean published;
    String address;
    String country;
    String city;
    String zip;
    LocalTime checkin;
    LocalTime checkout;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
