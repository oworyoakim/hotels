package com.hotels.properties.controllers;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hotels.properties.models.Amenity;
import com.hotels.properties.services.AmenityService;
import io.quarkus.security.Authenticated;
import lombok.AllArgsConstructor;

@Path("/properties/amenities")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@AllArgsConstructor
@Authenticated
public class ListAmenitiesController {
    private final AmenityService amenityService;
    @GET
    public Response index() {
        List<Amenity> amenities = amenityService.findAll();
        return Response.ok(amenities).build();
    }
}
