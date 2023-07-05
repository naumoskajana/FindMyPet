package com.example.findmypet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostPetDTO {

    private Long id;
    private String name;
    private String petType;
    private String photo;
    private String additionalInformation;
    private UserDTO petOwner;
    private Date lostAtTime;
    private LocationDTO lostAtLocation;
    private String status;
    private Date lastSeenAtDate;
    private LocationDTO lastSeenAtLocation;

}
