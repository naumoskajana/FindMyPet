package com.example.findmypet.web;

import com.example.findmypet.dto.PhotoGetDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @GetMapping("/get-photo")
    public ResponseEntity<Resource> getLostPetPhoto(@RequestBody PhotoGetDTO photoGetDTO) {
        Resource resource = new FileSystemResource(photoGetDTO.getPhotoPath());
        if (resource.exists()) {
            HttpHeaders headers = new HttpHeaders();

            MediaType mediaType = null;
            if (photoGetDTO.getPhotoPath().substring(photoGetDTO.getPhotoPath().lastIndexOf(".")+1).equals("jpg") ||
                    photoGetDTO.getPhotoPath().substring(photoGetDTO.getPhotoPath().lastIndexOf(".")+1).equals("jpeg")){
                mediaType = MediaType.IMAGE_JPEG;
            }
            if (photoGetDTO.getPhotoPath().substring(photoGetDTO.getPhotoPath().lastIndexOf(".")+1).equals("png")){
                mediaType = MediaType.IMAGE_PNG;
            }
            headers.setContentType(mediaType);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
