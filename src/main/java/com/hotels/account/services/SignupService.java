package com.hotels.account.services;

import com.hotels.account.models.Client;

import java.util.Optional;

public interface SignupService {
    public Optional<Client> createAccount(Client client);

    public String generatePassword();

    public String generatePassword(int length);

}
