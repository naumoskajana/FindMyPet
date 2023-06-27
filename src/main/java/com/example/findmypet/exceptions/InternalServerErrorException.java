package com.example.findmypet.exceptions;

public class InternalServerErrorException extends RuntimeException {
    private static final int STATUS_CODE = 500;
    private static final String msg = "Internal Server Error: Something went wrong.";

    public InternalServerErrorException(String... message) {
        super(message.length > 0 ? message[0] : msg);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}