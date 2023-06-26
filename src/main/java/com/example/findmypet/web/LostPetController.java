package com.example.findmypet.web;

import com.example.findmypet.dto.LostPetCreateDTO;
import com.example.findmypet.dto.LostPetDTO;
import com.example.findmypet.enumeration.PetType;
import com.example.findmypet.exceptions.CouldNotFetchAddressAndMunicipalityException;
import com.example.findmypet.exceptions.CouldNotSaveFileException;
import com.example.findmypet.exceptions.LostPetDoesNotExistException;
import com.example.findmypet.service.LostPetService;
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
@RequestMapping("/api/lost-pets")
public class LostPetController {

    private final LostPetService lostPetService;

    public LostPetController(LostPetService lostPetService) {
        this.lostPetService = lostPetService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LostPetDTO>> findAll(@RequestParam(required = false, defaultValue = "") String search,
                                    @RequestParam(required = false, defaultValue = "") List<PetType> types,
                                    @RequestParam(required = false, defaultValue = "") List<String> municipalities){
        return ResponseEntity.ok(lostPetService.findAll(search, types, municipalities));
    }

    @GetMapping("/id")
    public ResponseEntity<?> findById(@RequestParam Long lostPetId){
        try {
            return ResponseEntity.ok(lostPetService.findByIdDTO(lostPetId));
        } catch (LostPetDoesNotExistException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody LostPetCreateDTO lostPetCreateDTO){
        try {
            lostPetService.create(lostPetCreateDTO);
            return ResponseEntity.ok("Lost pet created successfully.");
        } catch (CouldNotFetchAddressAndMunicipalityException | LostPetDoesNotExistException | CouldNotSaveFileException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/pets-by-user")
    public ResponseEntity<List<LostPetDTO>> findAllByUserId(){
        // TODO: find all pets by the logged in user. Extract that info from Security Context or JWT
        return ResponseEntity.ok(lostPetService.findAllByUser("viktor-tasevski@hotmail.com"));
    }

    @GetMapping("/pet-types")
    public ResponseEntity<List<PetType>> findAllPetTypes(){
        return ResponseEntity.ok(lostPetService.findAllPetTypes());
    }
}
