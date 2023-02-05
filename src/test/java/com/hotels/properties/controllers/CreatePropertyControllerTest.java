package com.hotels.properties.controllers;

import java.time.LocalTime;
import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hotels.properties.enumerations.PropertyType;
import com.hotels.properties.models.Property;
import com.hotels.properties.requests.CreatePropertyRequest;
import com.hotels.properties.services.PropertyService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class CreatePropertyControllerTest {
    @InjectMock
    PropertyService propertyService;
    @Test
    @TestSecurity(user = "test@hotels.test")
    void store() {
        CreatePropertyRequest request = new CreatePropertyRequest();
        request.setName("Test Property");
        request.setPropertyType(PropertyType.APARTMENT.name());
        request.setDescription("Property description");
        request.setListingCurrency("UGX");
        request.setNightlyRate(100000);
        request.setCheckin("15:00");
        request.setCheckout("11:00");
        request.setAddress("Some address");
        request.setCountry("Some country");
        request.setCity("Some city");

        Property property = Property.builder()
            .id(1L)
            .listingCurrency(request.getListingCurrency())
            .name(request.getName())
            .description(request.getDescription())
            .clientId(1L)
            .userId(1L)
            .propertyType(PropertyType.valueOf(request.getPropertyType()))
            .nightlyRate(request.getNightlyRate())
            .bathrooms(2)
            .published(true)
            .address(request.getAddress())
            .country(request.getCountry())
            .city(request.getCity())
            .checkin(LocalTime.parse(request.getCheckin()))
            .checkout(LocalTime.parse(request.getCheckout()))
            .build();

        Optional<Property> optionalProperty = Optional.of(property);

        Mockito.when(propertyService.create(any(Property.class))).thenReturn(optionalProperty);

        given()
            .when()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON)
            .post("/properties")
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode())
            .body("id", equalTo(property.getId().intValue()))
            .body("name", is(property.getName()))
            .body("description", is(property.getDescription()))
            .body("propertyType", is(property.getPropertyType().name()));
    }

    @Test
    void storeUnauthorized() {
        CreatePropertyRequest request = new CreatePropertyRequest();
        request.setName("Test Property");
        request.setPropertyType(PropertyType.APARTMENT.name());
        request.setDescription("Property description");
        request.setListingCurrency("UGX");
        request.setNightlyRate(100000);
        request.setCheckin("15:00");
        request.setCheckout("11:00");
        request.setAddress("Some address");
        request.setCountry("Some country");
        request.setCity("Some city");

        given()
            .when()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON)
            .post("/properties")
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
