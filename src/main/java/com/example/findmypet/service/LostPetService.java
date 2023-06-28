package com.example.findmypet.service;

import com.example.findmypet.dto.LostPetCreateDTO;
import com.example.findmypet.dto.LostPetDTO;
import com.example.findmypet.entity.pets.LostPet;
import com.example.findmypet.enumeration.PetType;

import java.util.List;

public interface LostPetService {

    List<LostPetDTO> findAll(String keyword, List<PetType> petTypes, List<String> municipalities);
    LostPet findById(Long lostPetId);
    LostPetDTO findByIdDTO(Long lostPetId);
    void create(LostPetCreateDTO lostPetCreateDTO, String userEmail);
    List<LostPetDTO> findAllByUser(Long userId);
    List<PetType> findAllPetTypes();

}
