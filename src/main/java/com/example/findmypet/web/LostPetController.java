package com.example.findmypet.web;

import com.example.findmypet.dto.LostPetCreateDTO;
import com.example.findmypet.dto.LostPetDTO;
import com.example.findmypet.entity.user.User;
import com.example.findmypet.enumeration.PetType;
import com.example.findmypet.exceptions.CouldNotFetchAddressAndMunicipalityException;
import com.example.findmypet.exceptions.CouldNotSaveFileException;
import com.example.findmypet.exceptions.LostPetDoesNotExistException;
import com.example.findmypet.service.LostPetService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/lost-pets")
public class LostPetController {

    private final LostPetService lostPetService;

    public LostPetController(LostPetService lostPetService) {
        this.lostPetService = lostPetService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LostPetDTO>> findAll(@RequestParam(required = false, defaultValue = "", name = "search") String search,
                                                    @RequestParam(required = false, defaultValue = "", name = "types") List<PetType> types,
                                                    @RequestParam(required = false, defaultValue = "", name = "municipalities") List<String> municipalities){
        return ResponseEntity.ok(lostPetService.findAll(search, types, municipalities));
    }

    @GetMapping("/id")
    public ResponseEntity<?> findById(@RequestParam("lostPetId") Long lostPetId){
        try {
            return ResponseEntity.ok(lostPetService.findByIdDTO(lostPetId));
        } catch (LostPetDoesNotExistException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam("name") String name,
                                         @RequestParam("petType") PetType petType,
                                         @RequestParam("additionalInformation") String additionalInformation,
                                         @RequestParam("lostAtTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lostAtTime,
                                         @RequestParam("longitude") Double longitude,
                                         @RequestParam("latitude") Double latitude,
                                         @RequestPart("photo") MultipartFile photo){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LostPetCreateDTO lostPetCreateDTO = new LostPetCreateDTO();
            lostPetCreateDTO.setName(name);
            lostPetCreateDTO.setPetType(petType);
            lostPetCreateDTO.setAdditionalInformation(additionalInformation);
            lostPetCreateDTO.setLostAtTime(lostAtTime);
            lostPetCreateDTO.setLongitude(longitude);
            lostPetCreateDTO.setLatitude(latitude);
            lostPetCreateDTO.setPhoto(photo);
            lostPetService.create(lostPetCreateDTO, user.getEmail());
            return ResponseEntity.ok("Lost pet created successfully.");
        } catch (CouldNotFetchAddressAndMunicipalityException | LostPetDoesNotExistException | CouldNotSaveFileException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/pets-by-user")
    public ResponseEntity<List<LostPetDTO>> findAllByUserId(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(lostPetService.findAllByUser(user.getId()));
    }

    @GetMapping("/pet-types")
    public ResponseEntity<List<PetType>> findAllPetTypes(){
        return ResponseEntity.ok(lostPetService.findAllPetTypes());
    }

    @GetMapping("/delete/{lostPetId}")
    public void delete(@PathVariable Long lostPetId) {
        lostPetService.deleteById(lostPetId);
    }
}
