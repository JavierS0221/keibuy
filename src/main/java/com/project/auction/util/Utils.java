package com.project.auction.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public class Utils {


    public static String saveImage(MultipartFile multiPart, HttpServletRequest request) {
        // Obtenemos el nombre original del archivo
        String originalFilename = multiPart.getOriginalFilename();
        // Reemplazamos en el nombre de archivo los espacios por guiones
        originalFilename = originalFilename.replace(" ", "-");
        // Agregamos al nombre del archivo 8 caracteres aleatorios para evitar duplicados.
        String finalName = randomAlphaNumeric(8) + originalFilename;
        // Obtenemos la ruta ABSOLUTA del directorio images
        // apache-tomcat/webapps/auctionproject/resources/images/avatar/
        String finalPath = request.getServletContext().getRealPath("/resources/images/avatar/");
        try {
            // Formamos el nombre del archivo para guardarlo en el disco duro
            File imageFile = new File(finalPath + finalName);
            System.out.println(imageFile.getAbsolutePath());
            // Aqui se guarda fisicamente el archivo en el disco duro
            multiPart.transferTo(imageFile);
            return finalName;
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }
    }

    // Metodo para generar una cadena de longitud N de caracteres aleatorios.
    public static String randomAlphaNumeric(int count) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * CHARACTERS.length());
            builder.append(CHARACTERS.charAt(character));
        }
        return builder.toString();
    }
}
