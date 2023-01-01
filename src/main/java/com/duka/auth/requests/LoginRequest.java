package com.duka.auth.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message="Email Address is required")
    @Email(message="Email Address is invalid")
    String email;
    @NotBlank(message="Password is required")
    String password;
}
