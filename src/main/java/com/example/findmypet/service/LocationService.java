package com.example.findmypet.service;

import com.example.findmypet.entity.location.Location;

public interface LocationService {

    Location create (Double longitude, Double latitude, String municipality, String address);

}
