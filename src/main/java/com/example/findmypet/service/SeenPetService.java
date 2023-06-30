package com.example.findmypet.service;

import com.example.findmypet.dto.SeenPetCreateDTO;
import com.example.findmypet.dto.SeenPetDTO;
import com.example.findmypet.entity.pets.SeenPet;
import com.example.findmypet.entity.user.User;

import java.util.List;

public interface SeenPetService {

    void create(SeenPetCreateDTO seenPetCreateDTO, User user);
    List<SeenPetDTO> findAllByLostPet(Long lostPetId);
    SeenPet getLastSeenPetByLostPet(Long lostPetId);

}
