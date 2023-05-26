package com.example.findmypet.web;

import com.example.findmypet.dto.SeenPetCreateDTO;
import com.example.findmypet.dto.SeenPetDTO;
import com.example.findmypet.exceptions.CouldNotFetchAddressAndMunicipalityException;
import com.example.findmypet.exceptions.CouldNotSaveFileException;
import com.example.findmypet.exceptions.LostPetDoesNotExistException;
import com.example.findmypet.service.SeenPetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seen-pets")
public class SeenPetController {

    private final SeenPetService seenPetService;

    public SeenPetController(SeenPetService seenPetService) {
        this.seenPetService = seenPetService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody SeenPetCreateDTO seenPetCreateDTO){
        try {
            seenPetService.create(seenPetCreateDTO);
            return ResponseEntity.ok("Seen pet created successfully.");
        } catch (CouldNotFetchAddressAndMunicipalityException | LostPetDoesNotExistException | CouldNotSaveFileException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SeenPetDTO>> findAllByLostPet(@RequestParam Long lostPetId){
        return ResponseEntity.ok(seenPetService.findAllByLostPet(lostPetId));
    }
}
