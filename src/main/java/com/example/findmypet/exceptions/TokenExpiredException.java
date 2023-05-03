package com.example.findmypet.exceptions;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String token) {
        super(String.format("Token %s is expired.", token));
    }
}
