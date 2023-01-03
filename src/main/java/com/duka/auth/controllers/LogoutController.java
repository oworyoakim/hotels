package com.duka.auth.controllers;

import com.duka.auth.services.AuthService;
import com.duka.utils.JwtTokenUtil;
import io.quarkus.security.Authenticated;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@AllArgsConstructor
@RequestScoped
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class LogoutController {

    private final JsonWebToken token;

    private final AuthService authService;

    @Path("/logout")
    @POST
    public Response logout() {
        Boolean loggedOut = authService.logout(token);
        if (!loggedOut) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Object() {String message = "Could not verify token";})
                    .build();
        }
        return Response.status(Response.Status.OK).build();
    }


}
