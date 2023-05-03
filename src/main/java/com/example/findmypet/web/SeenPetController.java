package com.example.findmypet.web;

import com.example.findmypet.dto.SeenPetCreateDTO;
import com.example.findmypet.dto.SeenPetDTO;
import com.example.findmypet.service.SeenPetService;
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
    public void create(@RequestBody SeenPetCreateDTO seenPetCreateDTO){
        seenPetService.create(seenPetCreateDTO);
    }

    @GetMapping
    public List<SeenPetDTO> findAllByLostPet(@RequestParam Long lostPetId){
        return seenPetService.findAllByLostPet(lostPetId);
    }
}
