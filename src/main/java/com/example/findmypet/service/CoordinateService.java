package com.example.findmypet.service;

import com.example.findmypet.entity.location.Coordinate;

import java.util.List;

public interface CoordinateService {

    Coordinate create(Double longitude, Double latitude);
    void delete(Coordinate coordinates);
    void deleteAllByLocation(List<Coordinate> coordinates);

}
