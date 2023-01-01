package com.duka.auth.controllers;

import com.duka.auth.requests.LoginRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.Valid;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class LoginController {

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginRequest request) {
        return Response.ok(request).build();
    }
}
