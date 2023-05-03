package com.example.findmypet.dto;

import com.example.findmypet.enumeration.PetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostPetCreateDTO {

    private String name;
    private PetType petType;
    private String additionalInformation;
    private String userEmail;
    private Date lostAtTime;
    private Double longitude;
    private Double latitude;
    private MultipartFile photo;

}
