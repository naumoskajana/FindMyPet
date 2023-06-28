package com.example.findmypet.service.implementation;

import com.example.findmypet.config.file.FileUploadUtil;
import com.example.findmypet.config.map.MapUtil;
import com.example.findmypet.dto.AddressMunicipalityDTO;
import com.example.findmypet.dto.SeenPetCreateDTO;
import com.example.findmypet.dto.SeenPetDTO;
import com.example.findmypet.entity.pets.LostPet;
import com.example.findmypet.entity.pets.SeenPet;
import com.example.findmypet.enumeration.NotificationType;
import com.example.findmypet.repository.SeenPetRepository;
import com.example.findmypet.service.LocationService;
import com.example.findmypet.service.LostPetService;
import com.example.findmypet.service.NotificationService;
import com.example.findmypet.service.SeenPetService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeenPetServiceImpl implements SeenPetService {

    private final SeenPetRepository seenPetRepository;
    private final LostPetService lostPetService;
    private final LocationService locationService;
    private final NotificationService notificationService;

    public SeenPetServiceImpl(SeenPetRepository seenPetRepository,
                              LostPetService lostPetService,
                              LocationService locationService,
                              NotificationService notificationService) {
        this.seenPetRepository = seenPetRepository;
        this.lostPetService = lostPetService;
        this.locationService = locationService;
        this.notificationService = notificationService;
    }

    @Override
    public void create(SeenPetCreateDTO seenPetCreateDTO) {
        SeenPet seenPet = new SeenPet();
        LostPet lostPet = lostPetService.findById(seenPetCreateDTO.getLostPetId());
        seenPet.setLostPet(lostPet);
        seenPet.setSeenAtTime(seenPetCreateDTO.getSeenAtTime());
        AddressMunicipalityDTO addressMunicipalityDTO = MapUtil.getAddressAndMunicipality(seenPetCreateDTO.getLatitude(), seenPetCreateDTO.getLongitude());
        seenPet.setSeenAtLocation(
                locationService.create(
                        seenPetCreateDTO.getLongitude(),
                        seenPetCreateDTO.getLatitude(),
                        addressMunicipalityDTO.getMunicipality(),
                        addressMunicipalityDTO.getAddress()
                )
        );
        seenPetRepository.save(seenPet);
        seenPet.setPhoto("Pictures/" + seenPet.getId() + "/" + seenPetCreateDTO.getPhoto().getOriginalFilename());
        seenPetRepository.save(seenPet);
        notificationService.sendNotification("Нова локација", "Миленикот е виден на нова локација!", NotificationType.NEW_LOCATION, seenPet);
        try {
            FileUploadUtil.saveFile("Pictures/" + seenPet.getId(), seenPetCreateDTO.getPhoto().getOriginalFilename(), seenPetCreateDTO.getPhoto());
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    @Override
    public List<SeenPetDTO> findAllByLostPet(Long lostPetId) {
        return seenPetRepository.findAllByLostPet(lostPetId).stream().map(SeenPet::getAsSeenPetDTO).collect(Collectors.toList());
    }
}
