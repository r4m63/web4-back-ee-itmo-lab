package dev.ramil21.web4back.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.ramil21.web4back.config.SecurityConfig;
import dev.ramil21.web4back.model.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.rmi.ServerException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@ApplicationScoped
public class JwtTokenUtil {

    @Inject
    SecurityConfig securityConfig;

    public String generateJwtToken(Long userId, String email, Role role) throws ServerException {
        String secretKey = securityConfig.getJwtSecretKey();
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("role", role.toString())
                .withClaim("email", email)
                .withExpiresAt(Instant.now().plus(25, ChronoUnit.MINUTES))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String getEmailFromJwtToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("email").asString();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public Role getRoleFromJwtToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String roleStr = jwt.getClaim("role").asString();
            return Role.valueOf(roleStr);
        } catch (JWTDecodeException | IllegalArgumentException exception) {
            return null;
        }
    }

    public Long getUserIdFromJwtToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public boolean isJwtTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Date expirationTime = jwt.getExpiresAt();
            return expirationTime != null && expirationTime.before(new Date());
        } catch (JWTDecodeException exception) {
            return true; // Consider an undecodable token as expired/invalid
        }
    }

    public static String getTimeUntilJwtExpiration(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Date expirationTime = jwt.getExpiresAt();
            if (expirationTime != null) {
                long diff = expirationTime.getTime() - new Date().getTime();
                if (diff > 0) {
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    return String.format("%d days, %d hours, %d minutes, %d seconds (%d milliseconds)",
                            diffDays, diffHours, diffMinutes, diffSeconds, diff);
                }
            }
        } catch (JWTDecodeException exception) {
            System.err.println(exception.getMessage());
        }
        return "Expired";
    }


}
