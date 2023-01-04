package com.hotels.auth.models;

import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class AuthResponse {
    Long userId;
    String accessToken;
    Long expiresAt;
    boolean success;
    String message;
    Status status;
}
