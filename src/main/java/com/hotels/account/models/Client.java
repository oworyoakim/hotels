package com.hotels.account.models;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Value
public class Client {
    Long id;
    String subdomain;
    AccountType accountType;
    String name;
    String email;
    String phone;
    String address;
    String country;
    String city;
    String zip;
    Boolean active;
    String listingCurrency;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
