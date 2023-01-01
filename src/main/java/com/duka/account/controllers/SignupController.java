package com.duka.account.controllers;

import com.duka.account.models.AccountType;
import com.duka.account.models.Client;
import com.duka.account.requests.SignupRequest;
import com.duka.account.services.SignupService;
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

    private final SignupService signupService;
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
        Optional<Client> optionalClient = signupService.createAccount(client);
        if(optionalClient.isEmpty()) {
            throw new Exception("Failed to create account");
        }
        return Response.status(Response.Status.CREATED)
                .entity(optionalClient.get())
                .build();
    }
}
