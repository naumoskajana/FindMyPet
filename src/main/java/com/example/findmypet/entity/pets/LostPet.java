package com.example.findmypet.entity.pets;

import com.example.findmypet.dto.LostPetDTO;
import com.example.findmypet.enumeration.LostPetStatus;
import com.example.findmypet.enumeration.PetType;
import com.example.findmypet.entity.location.Location;
import com.example.findmypet.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lost_pets")
public class LostPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type")
    private PetType petType;

    @Column(name = "photo")
    private String photo;

    @Column(name = "additional_information")
    private String additionalInformation;

    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private User petOwner;

    @Column(name = "lost_at_time")
    private Date lostAtTime;

    @ManyToOne
    @JoinColumn(name = "lost_at_location_id")
    private Location lostAtLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LostPetStatus lostPetStatus;

    @JsonIgnore
    public LostPetDTO getAsLostPetDTO(){
        LostPetDTO lostPetDTO = new LostPetDTO();
        lostPetDTO.setId(this.id);
        lostPetDTO.setName(this.name);
        lostPetDTO.setPetType(this.petType.name());
        lostPetDTO.setPhoto(this.photo);
        lostPetDTO.setAdditionalInformation(this.additionalInformation);
        if (this.petOwner != null){
            lostPetDTO.setPetOwner(this.petOwner.getAsUserDTO());
        }
        lostPetDTO.setLostAtTime(this.lostAtTime);
        lostPetDTO.setLostAtLocation(this.lostAtLocation.getAsLocationDTO());
        lostPetDTO.setStatus(this.lostPetStatus.name());
        return lostPetDTO;
    }

}
