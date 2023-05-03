package com.example.findmypet.service.implementation;

import com.example.findmypet.entity.location.Location;
import com.example.findmypet.repository.LocationRepository;
import com.example.findmypet.service.CoordinateService;
import com.example.findmypet.service.LocationService;
import org.springframework.stereotype.Service;

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
}
