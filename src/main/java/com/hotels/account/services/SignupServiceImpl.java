package com.hotels.account.services;

import com.hotels.account.models.Client;
import com.hotels.db.DatabaseConnection;
import com.hotels.users.enumerations.UserType;
import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@ApplicationScoped
public class SignupServiceImpl implements SignupService {
    private final DatabaseConnection connection;

    private final AccountService accountService;

    public Optional<Client> createAccount(Client client) {
        return connection.getConnection().withHandle(handle -> {
            // create the account
            int rowCount = handle.createUpdate("""
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
                    .execute();

            if (rowCount == 0) {
                return Optional.empty();
            }

            Optional<Client> createdClient = accountService.findByEmailAddress(client.getEmail());

            if (createdClient.isEmpty()) {
                return createdClient;
            }


            // generate the temporary password
            String password = generatePassword();

            System.out.println("\n\n\nPassword: " + password);
            System.out.println("\n\n\n");


            // create the super user
            handle.createUpdate("""
                    INSERT INTO users (
                        type,
                        email,
                        clientId,
                        password,
                        createdAt,
                        updatedAt
                    ) VALUES (
                        :type,
                        :email,
                        :clientId,
                        :password,
                        :createdAt,
                        :updatedAt
                    )
                """).bind("type", UserType.BUSINESS_OWNER)
                    .bind("email", client.getEmail())
                    .bind("clientId", createdClient.get().getId())
                    .bind("password", BcryptUtil.bcryptHash(password))
                    .bind("createdAt", LocalDateTime.now())
                    .bind("updatedAt", LocalDateTime.now())
                    .execute();

            // Send the signup email with the temporary password



            // done
            return createdClient;
        });
    }


    public String generatePassword() {
        return this.generatePassword(10);
    }

    public String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return new String(password);
    }
}
