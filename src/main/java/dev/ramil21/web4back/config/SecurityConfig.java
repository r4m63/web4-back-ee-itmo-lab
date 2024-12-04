package dev.ramil21.web4back.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class SecurityConfig {

//    private Dotenv dotenv;
//
//    @PostConstruct
//    public void init() {
//        try {
//            log.info("Loading Dotenv");
//            dotenv = Dotenv.load();
//
//            if (dotenv == null) {
//                log.error("Dotenv could not be loaded. .env file might be missing.");
//                return;
//            }
//
//            log.info(".env loaded successfully");
//
//        } catch (Exception e) {
//            log.error("Error loading .env file", e);
//        }
//    }

    public String getJwtSecretKey() {
//        String key = dotenv.get("JWT_ACCESS_KEY");
//        if (key == null) {
//            log.error("ERROR TO GET JWT SECRET KEY FROM .env");
//            throw new IllegalArgumentException("JWT secret key not found in .env file.");
//        }
//        return key;
        return "JWT_SECRET";
    }

    public String getRefreshTokenSecretKey() {
//        String key = dotenv.get("JWT_REFRESH_KEY");
//        if (key == null) {
//            log.error("ERROR TO GET REFRESH TOKEN SECRET KEY FROM .env");
//            throw new IllegalArgumentException("Refresh token secret key not found in .env file.");
//        }
//        return key;
        return "REFRESH-TOKEN-SECRET";
    }

}
