package com.example.findmypet.entity.pets;

import com.example.findmypet.dto.SeenPetDTO;
import com.example.findmypet.entity.location.Location;
import com.example.findmypet.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "seen_pets")
public class SeenPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lost_pet_id")
    private LostPet lostPet;

    @Column(name = "photo")
    private String photo;

    @Column(name = "seen_at_time")
    private Date seenAtTime;

    @ManyToOne
    @JoinColumn(name = "seen_at_location_id")
    private Location seenAtLocation;

    @ManyToOne
    @JoinColumn(name = "reported_by_id")
    private User reportedBy;

    @JsonIgnore
    public SeenPetDTO getAsSeenPetDTO(){
        SeenPetDTO seenPetDTO = new SeenPetDTO();
        if (this.lostPet != null){
            seenPetDTO.setLostPet(this.lostPet.getAsLostPetDTO());
        }
        seenPetDTO.setSeenAtTime(this.seenAtTime);
        if (this.seenAtLocation != null){
            seenPetDTO.setSeenAtLocation(this.seenAtLocation.getAsLocationDTO());
        }
        if (this.reportedBy != null){
            seenPetDTO.setReportedBy(this.reportedBy.getAsUserDTO());
        }
        seenPetDTO.setPhoto(this.photo);
        return seenPetDTO;
    }

}
