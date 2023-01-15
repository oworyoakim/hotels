package com.hotels.properties.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Property {
    Long id;
    Long clientId;
    Long userId;
    String propertyTypeId;
    String listingCurrency;
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
