package com.hotels.auth.controllers;

import com.hotels.auth.models.AuthResponse;
import com.hotels.auth.requests.LoginRequest;
import com.hotels.auth.services.AuthService;
import lombok.AllArgsConstructor;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.validation.Valid;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
@AllArgsConstructor
public class LoginController {

    private final AuthService authService;

    @Path("/login")
    @POST
    public Response login(@Valid LoginRequest request) {
        AuthResponse response = authService.login(request);
        return Response.status(response.getStatus())
                .cookie(new NewCookie("accessToken", response.getAccessToken()))
                .entity(response)
                .build();
    }
}
