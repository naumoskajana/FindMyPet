package com.example.findmypet.web;

import com.example.findmypet.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/municipalities/all")
    public ResponseEntity<List<String>> getAllMunicipalities(){
        return ResponseEntity.ok(locationService.getAllMunicipalities());
    }
}
