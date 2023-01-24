package com.hotels.properties.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hotels.auth.models.ProfileResponse;
import com.hotels.auth.services.AuthService;
import com.hotels.properties.models.Property;
import com.hotels.properties.requests.CreatePropertyRequest;
import com.hotels.properties.services.PropertyService;
import io.quarkus.security.Authenticated;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/properties")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@AllArgsConstructor
@Authenticated
public class CreatePropertyController {
    private final JsonWebToken token;
    private final PropertyService propertyService;
    private final AuthService authService;
    @POST
    public Response store(@Valid CreatePropertyRequest request) {
        ProfileResponse loggedInUser = authService.loggedInUser(token);

        Property property = Property.builder()
            .name(request.getName())
            .description(request.getDescription())
            .nightlyRate(request.getNightlyRate())
            .listingCurrency(request.getListingCurrency())
            .country(request.getCountry())
            .city(request.getCity())
            .address(request.getAddress())
            .zip(request.getZip())
            .checkin(LocalTime.parse(request.getCheckin()))
            .checkout(LocalTime.parse(request.getCheckout()))
            .published(false)
            .clientId(loggedInUser.getClientId())
            .userId(loggedInUser.getId())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Optional<Property> optionalProperty = propertyService.create(property);

        if(optionalProperty.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.CREATED)
            .entity(optionalProperty.get())
            .build();
    }
}
