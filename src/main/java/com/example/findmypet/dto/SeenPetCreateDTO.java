package com.example.findmypet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeenPetCreateDTO {

    private Long lostPetId;
    private Date seenAtTime;
    private Double longitude;
    private Double latitude;
    private MultipartFile photo;

}
