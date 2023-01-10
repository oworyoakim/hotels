package com.hotels.utils;

import com.hotels.users.models.User;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.JsonWebToken;
import lombok.extern.slf4j.Slf4j;
import java.time.Instant;

@Slf4j
public class JwtTokenUtil {
    public static String generateToken(User user, Long duration, String issuer){
        long currentTimeInSecs = Instant.now().getEpochSecond();
        long expiresAt = currentTimeInSecs + duration;

        return Jwt.upn(user.getEmail())
                .claim("userId", user.getId())
                .issuer(issuer)
                .subject(user.getEmail())
                .issuedAt(currentTimeInSecs)
                .expiresAt(expiresAt)
                .sign();
    }

    public static Long getUserId(JsonWebToken token) {
        Long sessionUserId = null;
        try {
            sessionUserId = Long.valueOf(token.getClaim("userId").toString());
        } catch (Exception e) {
            log.error("Error getting userId claim from Token.", e);
        }
        return sessionUserId;
    }
}
