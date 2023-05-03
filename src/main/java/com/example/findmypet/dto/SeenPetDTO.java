package com.example.findmypet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeenPetDTO {

    private Long id;
    private LostPetDTO lostPet;
    private Date seenAtTime;
    private LocationDTO seenAtLocation;
    private UserDTO reportedBy;
    private String photo;

}
