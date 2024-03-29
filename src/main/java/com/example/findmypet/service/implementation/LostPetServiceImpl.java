package com.example.findmypet.service.implementation;

import com.example.findmypet.config.file.FileUploadUtil;
import com.example.findmypet.config.map.MapUtil;
import com.example.findmypet.dto.AddressMunicipalityDTO;
import com.example.findmypet.dto.LostPetCreateDTO;
import com.example.findmypet.dto.LostPetDTO;
import com.example.findmypet.entity.location.Location;
import com.example.findmypet.entity.pets.LostPet;
import com.example.findmypet.entity.pets.SeenPet;
import com.example.findmypet.enumeration.LostPetStatus;
import com.example.findmypet.enumeration.PetType;
import com.example.findmypet.exceptions.CouldNotFetchAddressAndMunicipalityException;
import com.example.findmypet.exceptions.CouldNotSaveFileException;
import com.example.findmypet.exceptions.LostPetDoesNotExistException;
import com.example.findmypet.repository.LostPetRepository;
import com.example.findmypet.service.LocationService;
import com.example.findmypet.service.LostPetService;
import com.example.findmypet.service.SeenPetService;
import com.example.findmypet.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LostPetServiceImpl implements LostPetService {

    private final LostPetRepository lostPetRepository;
    private final UserService userService;
    private final LocationService locationService;
    private final SeenPetService seenPetService;

    public LostPetServiceImpl(LostPetRepository lostPetRepository,
                              UserService userService,
                              LocationService locationService,
                              @Lazy SeenPetService seenPetService) {
        this.lostPetRepository = lostPetRepository;
        this.userService = userService;
        this.locationService = locationService;
        this.seenPetService = seenPetService;
    }

    @Override
    public List<LostPetDTO> findAll(String keyword, List<PetType> petTypes, List<String> municipalities) {
        List<LostPet> lostPets = lostPetRepository.findAllSearch(keyword.toLowerCase(), petTypes, municipalities.stream().map(String::toLowerCase).collect(Collectors.toList()));
        List<LostPetDTO> lostPetDTOS = new ArrayList<>();
        for (LostPet lostPet : lostPets){
            LostPetDTO lostPetDTO = lostPet.getAsLostPetDTO();
            SeenPet lastSeenPet = seenPetService.getLastSeenPetByLostPet(lostPet.getId());
            if (lastSeenPet != null){
                lostPetDTO.setLastSeenAtDate(lastSeenPet.getSeenAtTime());
                lostPetDTO.setLastSeenAtLocation(lastSeenPet.getSeenAtLocation().getAsLocationDTO());
            }
            lostPetDTOS.add(lostPetDTO);
        }
        return lostPetDTOS;
    }

    @Override
    public LostPet findById(Long lostPetId) {
        return lostPetRepository.findById(lostPetId).orElseThrow(() -> new LostPetDoesNotExistException(lostPetId));
    }

    @Override
    public LostPetDTO findByIdDTO(Long lostPetId) {
        return lostPetRepository.findById(lostPetId).orElseThrow(() -> new LostPetDoesNotExistException(lostPetId)).getAsLostPetDTO();
    }

    @Override
    public void create(LostPetCreateDTO lostPetCreateDTO, String userEmail) {
        LostPet lostPet = new LostPet();
        lostPet.setName(lostPetCreateDTO.getName());
        lostPet.setPetType(lostPetCreateDTO.getPetType());
        lostPet.setAdditionalInformation(lostPetCreateDTO.getAdditionalInformation());
        lostPet.setPetOwner(userService.findByEmail(userEmail));
        lostPet.setLostAtTime(lostPetCreateDTO.getLostAtTime());
        try {
            AddressMunicipalityDTO addressMunicipalityDTO = MapUtil.getAddressAndMunicipality(lostPetCreateDTO.getLatitude(), lostPetCreateDTO.getLongitude());
            lostPet.setLostAtLocation(
                    locationService.create(
                            lostPetCreateDTO.getLongitude(),
                            lostPetCreateDTO.getLatitude(),
                            addressMunicipalityDTO.getMunicipality(),
                            addressMunicipalityDTO.getAddress()
                    )
            );
        } catch (CouldNotFetchAddressAndMunicipalityException ex) {
            throw new CouldNotFetchAddressAndMunicipalityException();
        }
        lostPet.setLostPetStatus(LostPetStatus.LOST);
        lostPetRepository.save(lostPet);
        try {
            String filePath = FileUploadUtil.saveFile("Pictures/lost-pets/" + lostPet.getId(), lostPetCreateDTO.getPhoto().getOriginalFilename(), lostPetCreateDTO.getPhoto());
            String photo = filePath.replace("\\", "\\\\");
            lostPet.setPhoto(photo);
            lostPetRepository.save(lostPet);
        }
        catch (IOException e){
            throw new CouldNotSaveFileException("Could not save file.");
        }
    }

    @Override
    public List<LostPetDTO> findAllByUser(Long userId) {
        return lostPetRepository.findAllByUser(userId).stream().map(LostPet::getAsLostPetDTO).collect(Collectors.toList());
    }

    @Override
    public List<PetType> findAllPetTypes() {
        return Arrays.asList(PetType.values());
    }

    @Override
    public void deleteById(Long id) {
        LostPet lostPet = findById(id);
        Location location = lostPet.getLostAtLocation();
        seenPetService.deleteSeenPetsByLostPet(id);
        lostPetRepository.deleteById(id);
        locationService.delete(location);
    }

    @Override
    public void deleteLostPetsByUser(Long userId) {
        List<Long> lostPetsIds = lostPetRepository.findAllByUser(userId)
                .stream().map(LostPet::getId).collect(Collectors.toList());
        lostPetsIds.forEach(this::deleteById);
    }
}
