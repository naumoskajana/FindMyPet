package com.example.findmypet.service.implementation;

import com.example.findmypet.config.file.FileUploadUtil;
import com.example.findmypet.config.map.MapUtil;
import com.example.findmypet.dto.AddressMunicipalityDTO;
import com.example.findmypet.dto.SeenPetCreateDTO;
import com.example.findmypet.dto.SeenPetDTO;
import com.example.findmypet.entity.location.Location;
import com.example.findmypet.entity.pets.LostPet;
import com.example.findmypet.entity.pets.SeenPet;
import com.example.findmypet.entity.user.User;
import com.example.findmypet.enumeration.NotificationType;
import com.example.findmypet.repository.SeenPetRepository;
import com.example.findmypet.service.LocationService;
import com.example.findmypet.service.LostPetService;
import com.example.findmypet.service.NotificationService;
import com.example.findmypet.service.SeenPetService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
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
    public void create(SeenPetCreateDTO seenPetCreateDTO, User user) {
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
        seenPet.setReportedBy(user);
        seenPetRepository.save(seenPet);
        notificationService.sendNotification("Нова локација", "Миленикот е виден на нова локација!", NotificationType.NEW_SEEN_LOCATION, seenPet);
        if (seenPetCreateDTO.getPhoto() != null) {
            try {
                String filePath = FileUploadUtil.saveFile("Pictures/lost-pets/" + lostPet.getId(), seenPetCreateDTO.getPhoto().getOriginalFilename(), seenPetCreateDTO.getPhoto());
                String photo = filePath.replace("\\", "\\\\");
                seenPet.setPhoto(photo);
                seenPetRepository.save(seenPet);
            } catch (IOException e){
                System.out.println(e);
            }
        }
    }

    @Override
    public List<SeenPetDTO> findAllByLostPet(Long lostPetId) {
        return seenPetRepository.findAllByLostPet(lostPetId).stream().map(SeenPet::getAsSeenPetDTO).collect(Collectors.toList());
    }

    @Override
    public SeenPet getLastSeenPetByLostPet(Long lostPetId) {
        List<SeenPet> seenPets = seenPetRepository.findAllByLostPet(lostPetId);
        if (!seenPets.isEmpty()) {
            seenPets = seenPets.stream().sorted(Comparator.comparing(SeenPet::getSeenAtTime).reversed()).collect(Collectors.toList());
            return seenPets.get(0);
        }
        return null;
    }

    @Override
    public void deleteSeenPetsByLostPet(Long lostPetId) {
        List<SeenPet> seenPets = seenPetRepository.findAllByLostPet(lostPetId);
        List<Location> locations = seenPets.stream().map(SeenPet::getSeenAtLocation).collect(Collectors.toList());
        seenPetRepository.deleteAll(seenPets);
        locations.forEach(locationService::delete);
    }

}
