package com.duka.users.services;

import com.duka.db.DatabaseConnection;
import com.duka.users.models.User;
import com.duka.users.models.UserType;
import lombok.AllArgsConstructor;
import org.jdbi.v3.core.mapper.RowMapper;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.Optional;


@AllArgsConstructor
@ApplicationScoped
public class UserServiceImpl implements UserService {
    private final DatabaseConnection connection;

    private static final String SELECT_USER = """
        SELECT
            id,
            clientId,
            type,
            name,
            email,
            password,
            active,
            createdAt,
            updatedAt
        FROM
            users
        """;

    private static RowMapper<User> userRowMapper() {
        return (rs, ctx) -> User.builder()
                .id(rs.getLong("id"))
                .clientId(rs.getLong("clientId"))
                .type(UserType.valueOf(rs.getString("type")))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .active(rs.getBoolean("active"))
                .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updatedAt").toLocalDateTime())
                .build();
    }

    @Override
    public Optional<User> find(Long id) {
        return connection.getConnection().withHandle(handle -> handle.createQuery(SELECT_USER + " WHERE id = :id")
                .bind("id", id)
                .map(userRowMapper())
                .findFirst());
    }

    @Override
    public Optional<User> findByEmailAddress(String email) {
        return connection.getConnection().withHandle(handle -> handle.createQuery(SELECT_USER + " WHERE email = :email")
                .bind("email", email)
                .map(userRowMapper())
                .findFirst());
    }

    @Override
    public Integer updateToken(Long id, String token) {
        return connection.getConnection().withHandle(handle -> handle.createUpdate("UPDATE users SET token = :token WHERE id = :id")
                .bind("token", token)
                .bind("id", id)
                .bind("updatedAt", LocalDateTime.now())
                .execute());
    }
}
