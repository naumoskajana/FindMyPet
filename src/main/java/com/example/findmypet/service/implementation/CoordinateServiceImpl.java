package com.example.findmypet.service.implementation;

import com.example.findmypet.entity.location.Coordinate;
import com.example.findmypet.repository.CoordinateRepository;
import com.example.findmypet.service.CoordinateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinateServiceImpl implements CoordinateService {

    private final CoordinateRepository coordinateRepository;

    public CoordinateServiceImpl(CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    @Override
    public Coordinate create(Double longitude, Double latitude) {
        Coordinate coordinate = new Coordinate();
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        return coordinateRepository.save(coordinate);
    }

    @Override
    public void delete(Coordinate coordinates) {
        coordinateRepository.deleteById(coordinates.getId());
    }

}
