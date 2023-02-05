package com.hotels.account.services;

import com.hotels.account.models.Client;

import java.util.Optional;

public interface AccountService {
    Optional<Client> createAccount(Client client);
    Optional<Client> find(Long clientId);

    Optional<Client> findByEmailAddress(String email);
}
