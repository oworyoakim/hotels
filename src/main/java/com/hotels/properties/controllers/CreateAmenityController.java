package com.hotels.properties.controllers;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hotels.properties.requests.CreateAmenityRequest;
import io.quarkus.security.Authenticated;
import lombok.AllArgsConstructor;

@Path("/properties/amenities")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@AllArgsConstructor
@Authenticated
public class CreateAmenityController {

    @POST
    public Response store(@Valid CreateAmenityRequest request) {
        return Response.status(Response.Status.CREATED).build();
    }
}
