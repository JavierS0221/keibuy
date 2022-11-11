package com.project.auction.controller;

import com.project.auction.model.AvatarImage;
import com.project.auction.model.ItemImage;
import com.project.auction.service.AvatarImageService;
import com.project.auction.service.ItemImageService;
import com.project.auction.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    AvatarImageService avatarImageService;
    @Autowired
    ItemImageService itemImageService;

    @GetMapping("/avatar/{id}")
    @ResponseBody
    void showAvatarImage(@PathVariable("id") Long id, HttpServletResponse response, AvatarImage avatarImage)
            throws ServletException, IOException {
        avatarImage = avatarImageService.getImageById(id);
        byte[] decompressBytes = Utils.decompressImage(avatarImage.getBytes());
        response.setContentType(avatarImage.getContentType());
        response.getOutputStream().write(decompressBytes);
        response.getOutputStream().close();
    }

    @GetMapping("/item/{id}")
    @ResponseBody
    void showItemImage(@PathVariable("id") Long id, HttpServletResponse response, ItemImage itemImage)
            throws ServletException, IOException {
        itemImage = itemImageService.getImageById(id);
        byte[] decompressBytes = Utils.decompressImage(itemImage.getBytes());
        response.setContentType(itemImage.getContentType());
        response.getOutputStream().write(decompressBytes);
        response.getOutputStream().close();
    }
}
