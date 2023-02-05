package com.hotels.account.controllers;

import com.hotels.account.enumerations.AccountType;
import com.hotels.account.models.Client;
import com.hotels.account.requests.SignupRequest;
import com.hotels.account.services.AccountService;
import lombok.AllArgsConstructor;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Optional;

@Path("/signup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@AllArgsConstructor
public class SignupController {

    private final AccountService accountService;
    @POST
    public Response signup(@Valid SignupRequest request) throws Exception {
        Client client = Client.builder()
                .accountType(AccountType.valueOf(request.getAccountType()))
                .email(request.getEmail())
                .subdomain(request.getSubdomain())
                .phone(request.getPhone())
                .name(request.getName())
                .country(request.getCountry())
                .city(request.getCity())
                .zip(request.getZip())
                .address(request.getAddress())
                .active(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Optional<Client> optionalClient = accountService.createAccount(client);
        if(optionalClient.isEmpty()) {
            throw new Exception("Failed to create account");
        }
        return Response.status(Response.Status.CREATED)
                .entity(optionalClient.get())
                .build();
    }
}
