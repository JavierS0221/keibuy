package com.project.auction.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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

    /**
     * It generates a random string of length count, using the characters A-Z and 0-9
     *
     * @param count The length of the random string you want to create.
     * @return A random string of alphanumeric characters.
     */
    public static String randomAlphaNumeric(int count) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * CHARACTERS.length());
            builder.append(CHARACTERS.charAt(character));
        }
        return builder.toString();
    }

    /**
     * Deflate the input data and return the compressed data.
     *
     * @param data The byte array to compress.
     * @return A byte array
     */
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }


    /**
     * It takes a byte array, decompresses it, and returns a byte array.
     *
     * @param data The byte array of the image to be decompressed.
     * @return A byte array of the decompressed image.
     */
    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static String formatTime(long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if (days > 0) {
            return String.format("%02dd %02dh %02dm %02ds",
                    days,
                    hours,
                    minutes,
                    seconds);
        } else if (hours > 0) {
            return String.format("%02dh %02dm %02ds",
                    hours,
                    minutes,
                    seconds);
        } else if (minutes > 0) {
            return String.format("%02dm %02ds",
                    minutes,
                    seconds);
        } else {
            return String.format("%02ds",
                    seconds);
        }
    }
}
