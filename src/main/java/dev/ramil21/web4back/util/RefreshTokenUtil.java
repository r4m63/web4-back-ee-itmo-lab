package dev.ramil21.web4back.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.ramil21.web4back.config.SecurityConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class RefreshTokenUtil {

    @Inject
    SecurityConfig securityConfig;

    public String generateRefreshToken() {
        String secretKey = securityConfig.getJwtSecretKey();
        return JWT.create()
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

}
