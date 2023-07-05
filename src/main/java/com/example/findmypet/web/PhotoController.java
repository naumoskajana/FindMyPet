package com.example.findmypet.web;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @GetMapping("/get-photo/{photoPath}")
    public ResponseEntity<Resource> getLostPetPhoto(@PathVariable String photoPath) {
        try {
            String decodedPath = URLDecoder.decode(photoPath, "UTF-8");
            Resource resource = new FileSystemResource(photoPath);
            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();

                MediaType mediaType = null;
                if (decodedPath.substring(decodedPath.lastIndexOf(".") + 1).equals("jpg") ||
                        decodedPath.substring(decodedPath.lastIndexOf(".") + 1).equals("jpeg")) {
                    mediaType = MediaType.IMAGE_JPEG;
                }
                if (decodedPath.substring(decodedPath.lastIndexOf(".") + 1).equals("png")) {
                    mediaType = MediaType.IMAGE_PNG;
                }
                headers.setContentType(mediaType);

                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (UnsupportedEncodingException ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
