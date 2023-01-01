package com.duka.account.services;

import com.duka.account.models.Client;
import com.duka.account.requests.SignupRequest;

import java.util.Optional;

public interface SignupService {
    public Optional<Client> createAccount(Client client);

    Optional<Client> find(Long clientId);

    Optional<Client> findByEmailAddress(String email);

    public String generatePassword();

    public String generatePassword(int length);

}
