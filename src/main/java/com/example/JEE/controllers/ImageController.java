package com.example.JEE.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final String FOLDER_PATH = "C:/Users/qsdfghjklm/Desktop/projectTest/frontuser/src/assets/images/";

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        File file = new File(FOLDER_PATH + fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("Image non trouvée : " + fileName);
        }

        Path path = file.toPath();
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Changez selon le type d'image (JPEG/PNG)
                .body(resource);
    }
}
