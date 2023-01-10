package com.hotels.account.controllers;

import com.hotels.auth.models.ProfileResponse;
import com.hotels.auth.services.AuthService;
import com.hotels.users.models.User;
import com.hotels.users.services.UserService;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;


@AllArgsConstructor
@RequestScoped
@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
public class ProfileController {

    private final JsonWebToken token;

    private final AuthService authService;



    @GET
    public Response index() {
        ProfileResponse response = authService.loggedInUser(token);
        if(response == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(response).build();
    }
}
