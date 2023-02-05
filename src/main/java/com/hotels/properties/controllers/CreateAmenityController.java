package com.hotels.properties.controllers;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hotels.properties.enumerations.AmenityType;
import com.hotels.properties.models.Amenity;
import com.hotels.properties.requests.CreateAmenityRequest;
import com.hotels.properties.services.AmenityService;
import io.quarkus.security.Authenticated;
import lombok.AllArgsConstructor;

@Path("/properties/amenities")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@AllArgsConstructor
@Authenticated
public class CreateAmenityController {
    private final AmenityService amenityService;

    @POST
    public Response store(@Valid CreateAmenityRequest request) {
        Amenity amenity = Amenity.builder()
            .name(request.getName())
            .amenityType(AmenityType.valueOf(request.getAmenityType()))
            .avatar(request.getAvatar())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Optional<Amenity> optionalAmenity = amenityService.create(amenity);

        if (optionalAmenity.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.CREATED)
            .entity(optionalAmenity.get())
            .build();
    }
}
