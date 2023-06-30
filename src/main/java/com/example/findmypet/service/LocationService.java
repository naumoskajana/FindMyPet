package com.example.findmypet.service;

import com.example.findmypet.entity.location.Location;

import java.util.List;

public interface LocationService {

    Location create (Double longitude, Double latitude, String municipality, String address);
    List<String> getAllMunicipalities();
    void delete(Location location);
    void deleteAllBySeenPet(List<Location> locations);

}
