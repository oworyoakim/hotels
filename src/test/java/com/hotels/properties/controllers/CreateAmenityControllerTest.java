package com.hotels.properties.controllers;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hotels.properties.enumerations.AmenityType;
import com.hotels.properties.models.Amenity;
import com.hotels.properties.requests.CreateAmenityRequest;
import com.hotels.properties.services.AmenityService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
class CreateAmenityControllerTest {
    @InjectMock
    AmenityService amenityService;
    @Test
    @TestSecurity(user = "test@hotels.test")
    void store() {
        CreateAmenityRequest request = new CreateAmenityRequest();
        request.setAmenityType(AmenityType.PROPERTY_AMENITY.name());
        request.setName("WiFi");
        request.setAvatar("test-avatar");

        Amenity amenityCreated = Amenity.builder()
            .id(1L)
            .name(request.getName())
            .avatar(request.getAvatar())
            .amenityType(AmenityType.valueOf(request.getAmenityType()))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Optional<Amenity> optionalAmenity = Optional.of(amenityCreated);

        Mockito.when(amenityService.create(any(Amenity.class))).thenReturn(optionalAmenity);

        given()
            .when()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON)
            .post("/properties/amenities")
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode())
            .body("id", equalTo(amenityCreated.getId().intValue()))
            .body("name", is(amenityCreated.getName()))
            .body("avatar", is(amenityCreated.getAvatar()))
            .body("amenityType", is(amenityCreated.getAmenityType().name()));
    }

    @Test
    void storeUnauthorized() {
        CreateAmenityRequest request = new CreateAmenityRequest();
        request.setAmenityType(AmenityType.PROPERTY_AMENITY.name());
        request.setName("WiFi");
        request.setAvatar("test-avatar");
        given()
            .when()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON)
            .post("/properties/amenities")
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
