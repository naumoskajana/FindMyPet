package com.example.findmypet.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @GetMapping("/get-photo")
    public ResponseEntity<byte[]> getLostPetPhoto(@RequestParam String photoPath) {
        try {
            String decodedPath = URLDecoder.decode(photoPath, StandardCharsets.UTF_8.toString());
            Path absolutePath = Paths.get(decodedPath).toAbsolutePath();
            byte[] photoBytes = Files.readAllBytes(absolutePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
