package com.example.findmypet.exceptions;

public class CouldNotSetAuthenticationException extends RuntimeException {

    public CouldNotSetAuthenticationException() {
        super("Could not set user authentication.");
    }

}
