package com.example.findmypet.exceptions;

public class ExistingPasswordException extends RuntimeException {

    public ExistingPasswordException() {
        super("New password is the same as before.");
    }
}
