package com.hotels.auth.controllers;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hotels.auth.services.AuthService;
import io.quarkus.security.Authenticated;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

@AllArgsConstructor
@RequestScoped
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class RefreshTokenController {

    private final JsonWebToken token;

    private final AuthService authService;

    @Path("/refresh")
    @POST
    public Response refreshToken() {
        return Response.status(Response.Status.OK).build();
    }
}
