package com.duka.auth.services;

import com.duka.auth.models.AuthResponse;
import com.duka.auth.requests.LoginRequest;
import org.eclipse.microprofile.jwt.JsonWebToken;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    Boolean logout(JsonWebToken token);

    Boolean isAuthenticated(JsonWebToken token);
}
