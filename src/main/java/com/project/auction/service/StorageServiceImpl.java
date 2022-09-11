package com.project.auction.service;

import com.project.auction.exception.FileNotFoundException;
import com.project.auction.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    //esta sirve para indicar que este metodo se va a ejecutar cada vez que halla una nueva instancia de esta esta clase
    @PostConstruct
    @Override
    public void startFileStorage() {
        try {
            Files.createDirectories(Paths.get(storageLocation));
        }catch (IOException ex) {
            throw new StorageException("Error al inicializar la ubicación en el almacen de archivos");
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(file.isEmpty()) {
            throw new StorageException("No se puede almacenar un archivo vacio");
        }
        try {
            InputStream inputStream  = file.getInputStream();
            Files.copy(inputStream,Paths.get(storageLocation).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException excepcion) {
            throw new StorageException("Error al almacenar el archivo " + fileName,excepcion);
        }
        return fileName;
    }

    @Override
    public String storeFile(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        if(file.isEmpty()) {
            throw new StorageException("No se puede almacenar un archivo vacio");
        }

        Path saveTO = Paths.get(storageLocation).resolve(path);
        File folder = saveTO.toFile().getParentFile();
        if(!folder.exists()) {
            folder.mkdir();
        }
//        try {
//            try {
//                Files.copy(file.getInputStream(), saveTO);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException("Error : " + e.getMessage());
//            }
//            return "File uploaded successfully";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error : " + e.getMessage();
//        }

        try {
            InputStream inputStream  = file.getInputStream();
            Files.copy(inputStream,Paths.get(storageLocation).resolve(path), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException excepcion) {
            throw new StorageException("Error al almacenar el archivo " + fileName,excepcion);
        }
        return fileName;
    }

    @Override
    public Path getPath(String fileName) {
        return Paths.get(storageLocation).resolve(fileName);
    }

    @Override
    public Resource getResource(String fileName) {
        try {
            Path archivo = getPath(fileName);
            Resource resource = new UrlResource(archivo.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new FileNotFoundException("No se pudo encontrar el archivo " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("No se pudo encontrar el archivo " + fileName, ex);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        Path filePath = getPath(fileName);
        System.out.println("eliminar: "+filePath.toUri());
        try {
            FileSystemUtils.deleteRecursively(filePath);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
