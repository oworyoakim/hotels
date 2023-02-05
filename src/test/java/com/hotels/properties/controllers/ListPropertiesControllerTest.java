package com.hotels.properties.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.hotels.properties.enumerations.PropertyType;
import com.hotels.properties.models.Property;
import com.hotels.properties.services.PropertyService;
import com.hotels.users.services.UserService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;

@QuarkusTest
class ListPropertiesControllerTest {
    @InjectMock
    PropertyService propertyService;
    @Test
    @TestSecurity(user = UserService.TEST_EMAIL)
    void index() {
        Property property = Property.builder()
            .id(1L)
            .listingCurrency("UGX")
            .name("Test Property")
            .description("Property description")
            .clientId(1L)
            .userId(1L)
            .propertyType(PropertyType.APARTMENT)
            .nightlyRate(100000)
            .bathrooms(2)
            .published(true)
            .address("Some address")
            .country("Some country")
            .city("Some city")
            .checkin(LocalTime.of(15, 0))
            .checkout(LocalTime.of(11, 0))
            .build();
        List<Property> properties = new ArrayList<>();
        properties.add(property);

        Mockito.when(propertyService.findByClientId(anyLong())).thenReturn(properties);

        given()
            .when()
            .get("/properties")
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("$.size()", equalTo(properties.size()))
            .body("[0].name", is(property.getName()))
            .body("[0].description", is(property.getDescription()))
            .body("[0].id", is(property.getId().intValue()))
            .body("[0].clientId", is(property.getClientId().intValue()))
            .body("[0].propertyType", is(property.getPropertyType().name()));
    }

    @Test
    void indexUnauthorized() {
        when()
            .get("/properties")
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
