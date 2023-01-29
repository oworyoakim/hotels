package com.hotels.auth.services;

import com.hotels.auth.models.AuthResponse;
import com.hotels.auth.models.ProfileResponse;
import com.hotels.auth.requests.LoginRequest;
import com.hotels.db.DatabaseConnection;
import com.hotels.users.models.User;
import com.hotels.users.services.UserService;
import com.hotels.utils.JwtTokenUtil;
import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Optional;

@AllArgsConstructor
@ApplicationScoped
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    public static final String ACCOUNT_BLOCKED_MESSAGE = "Sorry, your account has been blocked by admin";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Sorry, your credentials are not valid";
    public static final String LOGIN_SUCCESSFUL_MESSAGE = "Success";

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userService.findByEmailAddress(loginRequest.getEmail());
        if(optionalUser.isEmpty()){
            // user not found
            return AuthResponse.builder()
                    .success(false)
                    .message(INVALID_CREDENTIALS_MESSAGE)
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        User user = optionalUser.get();


        if(!BcryptUtil.matches(loginRequest.getPassword(), user.getPassword())) {
            // passwords do not match
            return AuthResponse.builder()
                    .success(false)
                    .message(INVALID_CREDENTIALS_MESSAGE)
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        if(!user.getActive()){
            // user blocked
            return AuthResponse.builder()
                    .success(false)
                    .message(ACCOUNT_BLOCKED_MESSAGE)
                    .status(Response.Status.FORBIDDEN)
                    .build();
        }

        Long duration = ConfigProvider.getConfig().getValue("com.hotels.jwt.duration", Long.class);
        String issuer = ConfigProvider.getConfig().getValue("mp.jwt.verify.issuer", String.class);

        log.info("Duration: "+ duration);
        log.info("Issuer: "+ issuer);

        String accessToken = JwtTokenUtil.generateToken(user, duration, issuer);

        // update the db
        userService.updateToken(user.getId(), accessToken);


        return AuthResponse.builder()
                .success(true)
                .message(LOGIN_SUCCESSFUL_MESSAGE)
                .status(Response.Status.OK)
                .userId(user.getId())
                .accessToken(accessToken)
                .expiresAt(Instant.now().getEpochSecond() + duration)
                .build();
    }

    @Override
    public Boolean logout(JsonWebToken token) {
        Long userId = Long.valueOf(token.getClaim("userId").toString());
        Integer rowsCount = userService.updateToken(userId, null);
        return rowsCount == 1;
    }

    @Override
    public Boolean isAuthenticated(JsonWebToken token) {
        var userIdClaim = token.getClaim("userId");

        if (userIdClaim == null) {
            return false;
        }

        Long userId = Long.valueOf(userIdClaim.toString());

        if (Instant.now().isAfter(Instant.ofEpochSecond(token.getExpirationTime()))) {
            return false;
        }

        Optional<User> optionalUser = userService.find(userId);
        return optionalUser.isPresent() && token.getRawToken().equals(optionalUser.get().getToken());
    }

    @Override
    public ProfileResponse loggedInUser(JsonWebToken token) {
        String email = token.getName();
        Optional<User> optionalUser = userService.findByEmailAddress(email);

        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        return ProfileResponse.builder()
                .id(user.getId())
                .type(user.getType())
                .name(user.getName())
                .email(user.getEmail())
                .clientId(user.getClientId())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
