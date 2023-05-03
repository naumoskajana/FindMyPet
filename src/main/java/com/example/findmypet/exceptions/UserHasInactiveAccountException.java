package com.example.findmypet.exceptions;

public class UserHasInactiveAccountException extends RuntimeException {

    public UserHasInactiveAccountException(String email) {
        super(String.format("User with email %d has inactive account", email));
    }
}
