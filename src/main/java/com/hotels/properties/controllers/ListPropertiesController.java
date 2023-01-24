package com.hotels.properties.controllers;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hotels.auth.models.ProfileResponse;
import com.hotels.auth.services.AuthService;
import com.hotels.properties.models.Property;
import com.hotels.properties.services.PropertyService;
import io.quarkus.security.Authenticated;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/properties")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@AllArgsConstructor
@Authenticated
public class ListPropertiesController {
    private final JsonWebToken token;
    private final PropertyService propertyService;
    private final AuthService authService;
    @GET
    public Response index() {
        ProfileResponse loggedInUser = authService.loggedInUser(token);

        List<Property> properties = propertyService.findByClientId(loggedInUser.getClientId());

        return Response.ok(properties).build();
    }
}
