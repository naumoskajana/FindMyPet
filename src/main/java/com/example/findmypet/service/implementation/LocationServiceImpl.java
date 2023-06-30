package com.example.findmypet.service.implementation;

import com.example.findmypet.entity.location.Coordinate;
import com.example.findmypet.entity.location.Location;
import com.example.findmypet.repository.LocationRepository;
import com.example.findmypet.service.CoordinateService;
import com.example.findmypet.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final CoordinateService coordinateService;

    public LocationServiceImpl(LocationRepository locationRepository, CoordinateService coordinateService) {
        this.locationRepository = locationRepository;
        this.coordinateService = coordinateService;
    }

    @Override
    public Location create(Double longitude, Double latitude, String municipality, String address) {
        Location location = new Location();
        location.setCoordinates(coordinateService.create(longitude, latitude));
        location.setMunicipality(municipality);
        location.setAddress(address);
        return locationRepository.save(location);
    }

    @Override
    public List<String> getAllMunicipalities() {
        return locationRepository.findAll().stream().map(Location::getMunicipality).collect(Collectors.toList());
    }

    @Override
    public void delete(Location location) {
        Coordinate coordinates = location.getCoordinates();
        locationRepository.delete(location);
        coordinateService.delete(coordinates);
    }

    @Override
    public void deleteAllBySeenPet(List<Location> locations) {
        List<Coordinate> coordinates = locations.stream().map(Location::getCoordinates).collect(Collectors.toList());
        locationRepository.deleteAll(locations);
        coordinateService.deleteAllByLocation(coordinates);
    }
}
