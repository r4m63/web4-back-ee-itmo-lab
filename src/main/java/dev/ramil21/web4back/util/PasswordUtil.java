package dev.ramil21.web4back.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(char[] password) {
        // The cost factor determines the amount of computation required for hashing.
        // A higher number means more work, which increases security but also slows down hashing.
        int cost = 12; // This is a commonly used cost factor.

        return BCrypt.withDefaults().hashToString(cost, password);
    }

    public static boolean checkPassword(char[] password, String hashedPassword) {
        return BCrypt.verifyer().verify(password, hashedPassword).verified;
    }

}
