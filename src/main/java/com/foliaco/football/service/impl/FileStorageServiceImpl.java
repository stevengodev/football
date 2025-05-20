package com.foliaco.football.service.impl;

import com.foliaco.football.service.FileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final String UPLOAD_DIR = "uploads";

    public FileStorageServiceImpl() {
        Path path = Paths.get(UPLOAD_DIR);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't create the directory", e);
            }
        }

    }

    @Override
    public String storeFile(MultipartFile file) {
       
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
        Path filePath = Paths.get(UPLOAD_DIR).resolve(uniqueFileName).normalize();


        if (!filePath.startsWith(UPLOAD_DIR)) {
            throw new SecurityException("Unauthorized access attempt to a path outside the allowed directory");
        }

        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }

        if (originalFileName.contains("..")) {
            throw new RuntimeException("Invalid file name");
        }

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't store the file", e);
        }

    }

    @Override
    public void deleteFile(String fileName) {

        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();

        try {

            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found");
            }

            if (!filePath.startsWith(UPLOAD_DIR)) {
                throw new SecurityException("Unauthorized access attempt to a path outside the allowed directory");
            }

            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't delete the file", e);
        }

    }

    @Override
    public boolean fileExists(String fileName) {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
        return Files.exists(filePath);
    }

    
}
