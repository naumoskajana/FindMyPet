package com.example.findmypet.web;

import com.example.findmypet.dto.LostPetCreateDTO;
import com.example.findmypet.dto.LostPetDTO;
import com.example.findmypet.enumeration.PetType;
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
    public List<LostPetDTO> findAll(){
        return lostPetService.findAll();
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
    public void create(@RequestBody LostPetCreateDTO lostPetCreateDTO){
        lostPetService.create(lostPetCreateDTO);
    }

    @GetMapping("/pets-by-user")
    public List<LostPetDTO> findAllByUserId(@RequestParam String email){
        return lostPetService.findAllByUser(email);
    }

    @GetMapping("/pet-types")
    public List<PetType> findAllPetTypes(){
        return lostPetService.findAllPetTypes();
    }
}
