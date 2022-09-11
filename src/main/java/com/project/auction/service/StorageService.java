package com.project.auction.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    public void startFileStorage();

    public String storeFile(MultipartFile file);
    public String storeFile(MultipartFile file, String path);

    public Path getPath(String fileName);

    public Resource getResource(String fileName);

    public void deleteFile(String fileName);
}
