package com.duka.account.requests;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignupRequest {
    @NotBlank(message = "Account type is required")
    String accountType;

    @NotBlank(message = "Email address is required")
    @Email(message = "Email address is invalid")
    String email;

    @NotBlank(message = "Subdomain is required")
    String subdomain;

    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Phone is required")
    String phone;

    @NotBlank(message = "Address is required")
    String address;

    @NotBlank(message = "City is required")
    String city;

    @NotBlank(message = "Country is required")
    String country;

    String zip;

}
