package com.example.findmypet.exceptions;

public class CouldNotFetchAddressAndMunicipalityException extends RuntimeException {

    public CouldNotFetchAddressAndMunicipalityException() {
        super("Could not fetch the address and municipality from OpenStreetMap Nominatim API.");
    }

}
