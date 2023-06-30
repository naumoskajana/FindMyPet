package com.example.findmypet.service;

import com.example.findmypet.entity.location.Coordinate;

public interface CoordinateService {

    Coordinate create(Double longitude, Double latitude);
    void delete(Coordinate coordinates);

}
