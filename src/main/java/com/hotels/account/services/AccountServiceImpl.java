package com.hotels.account.services;

import com.hotels.account.enumerations.AccountType;
import com.hotels.account.models.Client;
import com.hotels.db.DatabaseConnection;
import com.hotels.users.enumerations.UserType;
import com.hotels.users.models.User;
import com.hotels.users.services.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.AllArgsConstructor;
import org.jdbi.v3.core.mapper.RowMapper;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@ApplicationScoped
public class AccountServiceImpl implements AccountService {
    private final DatabaseConnection connection;
    private final UserService userService;

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

    public Optional<Client> createAccount(Client client) {
        return connection.getConnection().withHandle(handle -> {
            // create the account
            Long clientId = handle.createUpdate("""
                    INSERT INTO clients (
                        accountType,
                        subdomain,
                        name,
                        email,
                        phone,
                        country,
                        city,
                        zip,
                        address,
                        createdAt,
                        updatedAt
                    ) VALUES (
                        :accountType,
                        :subdomain,
                        :name,
                        :email,
                        :phone,
                        :country,
                        :city,
                        :zip,
                        :address,
                        :createdAt,
                        :updatedAt
                    )
                """).bind("accountType", client.getAccountType())
                .bind("subdomain", client.getSubdomain())
                .bind("name", client.getName())
                .bind("email", client.getEmail())
                .bind("phone", client.getPhone())
                .bind("country", client.getCountry())
                .bind("city", client.getCity())
                .bind("address", client.getAddress())
                .bind("zip", client.getZip())
                .bind("createdAt", client.getCreatedAt())
                .bind("updatedAt", client.getUpdatedAt())
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Long.class)
                .one();

            if (clientId == null) {
                return Optional.empty();
            }

            // generate the temporary password
            String password = userService.generatePassword();

            System.out.println("\n\n\nPassword: " + password);
            System.out.println("\n\n\n");


            // create the superuser
            User superUser = User.builder()
                .clientId(clientId)
                .type(UserType.BUSINESS_OWNER)
                .email(client.getEmail())
                .password(BcryptUtil.bcryptHash(password))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

            Optional<User> optionalUser = userService.create(superUser);

            if (optionalUser.isEmpty()) {
                return Optional.empty();
            }

            // Send the signup email with the temporary password

            // done
            return find(clientId);
        });
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
