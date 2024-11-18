package com.example.JEE.services;

import com.example.JEE.entities.FileData;
import com.example.JEE.repositories.FileDataRepository;
import com.example.JEE.repositories.StorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH="C:/Users/qsdfghjklm/Desktop/stage/photo";



    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath= file.getOriginalFilename();

        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(FOLDER_PATH+filePath));

        if (fileData != null) {
            return  filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileDataOptional = fileDataRepository.findFirstByName(fileName);

        if (fileDataOptional.isPresent()) {

            FileData fileData = fileDataOptional.get();
            String filePath = fileData.getFilePath();
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return images;
        } else {
            // Handle case where file data is not found
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }





}