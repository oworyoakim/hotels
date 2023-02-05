package com.hotels.properties.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

import com.hotels.properties.enumerations.AmenityType;
import com.hotels.properties.models.Amenity;
import com.hotels.properties.services.AmenityService;
import com.hotels.users.services.UserService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ListAmenitiesControllerTest {
    @InjectMock
    AmenityService amenityService;

    @Test
    @TestSecurity(user = UserService.TEST_EMAIL)
    void index() {
        Amenity amenity = Amenity.builder()
            .id(1L)
            .name("WiFi")
            .avatar("avatar-link")
            .amenityType(AmenityType.PROPERTY_AMENITY)
            .build();

        List<Amenity> amenities = new ArrayList<>();
        amenities.add(amenity);

        Mockito.when(amenityService.findAll()).thenReturn(amenities);

        given()
            .when()
            .get("/properties/amenities")
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("$.size()", equalTo(amenities.size()))
            .body("[0].name", is(amenity.getName()))
            .body("[0].avatar", is(amenity.getAvatar()))
            .body("[0].id", is(amenity.getId().intValue()))
            .body("[0].amenityType", is(amenity.getAmenityType().name()));
    }


    @Test
    void indexUnauthorized() {
        when()
            .get("/properties/amenities")
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
