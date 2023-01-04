package com.hotels.account.services;

import com.hotels.account.models.AccountType;
import com.hotels.account.models.Client;
import com.hotels.db.DatabaseConnection;
import lombok.AllArgsConstructor;
import org.jdbi.v3.core.mapper.RowMapper;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@AllArgsConstructor
@ApplicationScoped
public class AccountServiceImpl implements AccountService {
    private final DatabaseConnection connection;

    private static final String SELECT_CLIENT = """
        SELECT
            id,
            accountType,
            subdomain,
            name,
            email,
            phone,
            address,
            country,
            city,
            zip,
            active,
            createdAt,
            updatedAt
        FROM
            clients
        """;

    private static RowMapper<Client> clientRowMapper() {
        return (rs, ctx) -> Client.builder()
                .id(rs.getLong("id"))
                .accountType(AccountType.valueOf(rs.getString("accountType")))
                .subdomain(rs.getString("subdomain"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .address(rs.getString("address"))
                .country(rs.getString("country"))
                .city(rs.getString("city"))
                .zip(rs.getString("zip"))
                .active(rs.getBoolean("active"))
                .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updatedAt").toLocalDateTime())
                .build();
    }

    @Override
    public Optional<Client> find(Long id) {
        return connection.getConnection().withHandle(handle -> handle.createQuery(SELECT_CLIENT + " WHERE id = :id")
                .bind("id", id)
                .map(clientRowMapper())
                .findFirst());
    }

    @Override
    public Optional<Client> findByEmailAddress(String email) {
        return connection.getConnection().withHandle(handle -> handle.createQuery(SELECT_CLIENT + " WHERE email = :email")
                .bind("email", email)
                .map(clientRowMapper())
                .findFirst());
    }
}
