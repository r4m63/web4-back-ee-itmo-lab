package dev.ramil21.web4back.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;

@ApplicationScoped
public class SecurityConfig {

    private Dotenv dotenv;

    @PostConstruct
    public void init() {
        dotenv = Dotenv.load();
    }

    public String getJwtSecretKey() {
        String key = dotenv.get("JWT_ACCESS_KEY");
        if (key == null) {
            throw new IllegalArgumentException("JWT secret key not found in .env file.");
        }
        return key;
    }

    public String getRefreshTokenSecretKey() {
        String key = dotenv.get("JWT_REFRESH_KEY");
        if (key == null) {
            throw new IllegalArgumentException("Refresh token secret key not found in .env file.");
        }
        return key;
    }

    public String getDatabaseUrl() {
        String url = dotenv.get("DATABASE_URL");
        if (url == null) {
            throw new IllegalArgumentException("Database URL not found in .env file.");
        }
        return url;
    }

    /*
    public static void main(String[] args) {
        SecurityConfig config = new SecurityConfig();
        config.init();
        System.out.println(config.getJwtSecretKey());
        System.out.println(config.getRefreshTokenSecretKey());
    }
    */

}
