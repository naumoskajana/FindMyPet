package com.example.findmypet.web;

import com.example.findmypet.dto.SeenPetCreateDTO;
import com.example.findmypet.dto.SeenPetDTO;
import com.example.findmypet.exceptions.CouldNotFetchAddressAndMunicipalityException;
import com.example.findmypet.exceptions.CouldNotSaveFileException;
import com.example.findmypet.exceptions.LostPetDoesNotExistException;
import com.example.findmypet.service.SeenPetService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/seen-pets")
public class SeenPetController {

    private final SeenPetService seenPetService;

    public SeenPetController(SeenPetService seenPetService) {
        this.seenPetService = seenPetService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam("lostPetId") Long lostPetId,
                                         @RequestParam("seenAtTime") @DateTimeFormat(pattern="yyyy-MM-dd") Date seenAtTime,
                                         @RequestParam("longitude") Double longitude,
                                         @RequestParam("latitude") Double latitude,
                                         @RequestPart("photo") MultipartFile photo){
        try {
            SeenPetCreateDTO seenPetCreateDTO = new SeenPetCreateDTO();
            seenPetCreateDTO.setLostPetId(lostPetId);
            seenPetCreateDTO.setSeenAtTime(seenAtTime);
            seenPetCreateDTO.setLongitude(longitude);
            seenPetCreateDTO.setLatitude(latitude);
            seenPetCreateDTO.setPhoto(photo);
            seenPetService.create(seenPetCreateDTO);
            return ResponseEntity.ok("Seen pet created successfully.");
        } catch (CouldNotFetchAddressAndMunicipalityException | LostPetDoesNotExistException | CouldNotSaveFileException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SeenPetDTO>> findAllByLostPet(@RequestParam("lostPetId") Long lostPetId){
        return ResponseEntity.ok(seenPetService.findAllByLostPet(lostPetId));
    }
}
