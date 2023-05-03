package com.example.findmypet.exceptions;

public class LostPetDoesNotExistException extends RuntimeException {

    public LostPetDoesNotExistException(Long id) {
        super(String.format("Lost pet with %d does not exist", id));
    }
}
