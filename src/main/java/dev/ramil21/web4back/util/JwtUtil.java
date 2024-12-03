package dev.ramil21.web4back.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtUtil {

    public String generateToken(String username, UserRole role, Long userId, String email) throws ServerException {
        try {
            String secretKey = SecurityConfig.getJwtSecretKey();
            return JWT.create()
                    .withSubject(username)
                    .withClaim("userId", userId)
                    .withClaim("role", role.toString())
                    .withClaim("email", email)
                    // Set expiry to 25 minutes
                    .withExpiresAt(Instant.now().plus(25, ChronoUnit.MINUTES))
                    .sign(Algorithm.HMAC256(secretKey));
        } catch (ConfigurationException e) {
            throw new ServerException("Internal server error.", e);
        }
    }

    public String getEmailFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("email").asString();
        } catch (JWTDecodeException exception) {
            log.error("Error decoding token: {}", exception.getMessage());
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public Role getRoleFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String roleStr = jwt.getClaim("role").asString();
            return Role.valueOf(roleStr);
        } catch (JWTDecodeException | IllegalArgumentException exception) {
            return null;
        }
    }

    public Long getUserIdFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Date expirationTime = jwt.getExpiresAt();
            return expirationTime != null && expirationTime.before(new Date());
        } catch (JWTDecodeException exception) {
            return true; // Consider an undecodable token as expired/invalid
        }
    }

    public static String getTimeUntilExpiration(String token) {
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
