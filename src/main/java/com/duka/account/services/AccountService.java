package com.duka.account.services;

import com.duka.account.models.Client;

import java.util.Optional;

public interface AccountService {

    Optional<Client> find(Long clientId);

    Optional<Client> findByEmailAddress(String email);
}
