package com.hotels.properties.requests;

import java.time.LocalTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hotels.properties.enumerations.PropertyType;
import lombok.Data;

@Data
public class CreatePropertyRequest {
    @NotBlank(message="Property name is required")
    String name;
    @NotBlank(message = "Property type is required")
    String propertyType;
    String description;
    @NotNull(message = "Please provide a nightly rate for this property")
    Integer nightlyRate;
    @NotBlank(message = "Please provide a listing currency")
    String listingCurrency;
    @NotBlank(message = "Checkin time is required")
    String checkin;
    @NotBlank(message = "Checkout time is required")
    String checkout;

    @NotBlank(message = "Address is required")
    String address;
    @NotBlank(message = "Country is required")
    String country;
    @NotBlank(message = "City is required")
    String city;
    String zip;
}
