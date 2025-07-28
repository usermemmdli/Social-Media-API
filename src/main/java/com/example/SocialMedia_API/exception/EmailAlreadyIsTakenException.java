package com.example.SocialMedia_API.exception;

public class EmailAlreadyIsTakenException extends RuntimeException {
    public EmailAlreadyIsTakenException(String message) {
        super(message);
    }
}
