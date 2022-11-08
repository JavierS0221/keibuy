package com.project.auction.controller;

import com.project.auction.model.AvatarImage;
import com.project.auction.service.ImageService;
import com.project.auction.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("image")
public class ImageController {

    @Value("${uploadDir}")
    private String uploadFolder;
    @Autowired
    ImageService imageService;

    @GetMapping("/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, AvatarImage avatarImage)
            throws ServletException, IOException {
        avatarImage = imageService.getImageById(id);
        byte[] decompressBytes = Utils.decompressImage(avatarImage.getBytes());
        response.setContentType(avatarImage.getContentType());
        response.getOutputStream().write(decompressBytes);
        response.getOutputStream().close();
    }
}
