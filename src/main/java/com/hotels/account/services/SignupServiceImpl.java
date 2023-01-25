package com.hotels.account.services;

import com.hotels.account.models.Client;
import com.hotels.db.DatabaseConnection;
import com.hotels.users.enumerations.UserType;
import com.hotels.users.models.User;
import com.hotels.users.services.UserService;
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
    private final UserService userService;

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
            String password = generatePassword();

            System.out.println("\n\n\nPassword: " + password);
            System.out.println("\n\n\n");


            // create the super user
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
            return accountService.find(clientId);
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
