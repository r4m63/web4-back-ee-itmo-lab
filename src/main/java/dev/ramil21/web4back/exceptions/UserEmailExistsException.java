package dev.ramil21.web4back.exceptions;

public class UserEmailExistsException extends Exception {
    public UserEmailExistsException(String message) {
        super(message);
    }
}
