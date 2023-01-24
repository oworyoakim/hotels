package com.hotels.properties.requests;

import java.time.LocalTime;
import javax.validation.constraints.NotBlank;

import com.hotels.properties.enumerations.PropertyType;
import lombok.Data;

@Data
public class CreatePropertyRequest {
    @NotBlank(message="Property name is required")
    String name;
    @NotBlank(message = "Property type is required")
    PropertyType propertyType;
    String description;
    @NotBlank(message = "Please provide a nightly rate for this property")
    Double nightlyRate;
    Integer bathrooms;
    @NotBlank(message = "Please provide a listing currency")
    String listingCurrency;
    @NotBlank(message = "Checkin time is required")
    LocalTime checkin;
    @NotBlank(message = "Checkout time is required")
    LocalTime checkout;

    @NotBlank(message = "Address is required")
    String address;
    @NotBlank(message = "Country is required")
    String country;
    @NotBlank(message = "City is required")
    String city;
    String zip;
}
