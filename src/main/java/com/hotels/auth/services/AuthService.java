package com.hotels.auth.services;

import com.hotels.auth.models.AuthResponse;
import com.hotels.auth.models.ProfileResponse;
import com.hotels.auth.requests.LoginRequest;
import org.eclipse.microprofile.jwt.JsonWebToken;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    Boolean logout(JsonWebToken token);

    Boolean isAuthenticated(JsonWebToken token);

    ProfileResponse loggedInUser(JsonWebToken token);
}
