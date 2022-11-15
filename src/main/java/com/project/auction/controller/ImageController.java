package com.project.auction.controller;

import com.project.auction.model.AvatarImage;
import com.project.auction.model.ItemImage;
import com.project.auction.model.Person;
import com.project.auction.service.AvatarImageService;
import com.project.auction.service.ItemImageService;
import com.project.auction.service.PersonService;
import com.project.auction.util.Utils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
@RequestMapping("image")
public class ImageController {
    @Autowired
    AvatarImageService avatarImageService;
    @Autowired
    ItemImageService itemImageService;
    @Autowired
    PersonService personService;
    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/avatar/{id}")
    @ResponseBody
    void showAvatarImage(@PathVariable("id") Long id, HttpServletResponse response, AvatarImage avatarImage)
            throws IOException {
        avatarImage = avatarImageService.getImageById(id);
        if (avatarImage == null) {
            Resource resource = resourceLoader.getResource("classpath:/static/images/DefaultAvatar.png");
            InputStream inputStream = resource.getInputStream();
            response.setContentType("image/png");
            response.getOutputStream().write(FileCopyUtils.copyToByteArray(inputStream));
            response.getOutputStream().close();
        } else {
            byte[] decompressBytes = Utils.decompressImage(avatarImage.getBytes());
            response.setContentType(avatarImage.getContentType());
            response.getOutputStream().write(decompressBytes);
            response.getOutputStream().close();
        }
    }

    @GetMapping("/avatar/person/{id}")
    @ResponseBody
    void showAvatarImageFromPerson(@PathVariable("id") Long id, HttpServletResponse response, Person person)
            throws IOException {
        AvatarImage avatarImage = null;
        for (Person personListed : personService.listPersons()) {
            if (personListed.getId() == id) {
                person = personListed;
                break;
            }
        }

        if (person != null) {
            avatarImage = person.getAvatar();
        }

        if (avatarImage == null) {
            Resource resource = resourceLoader.getResource("classpath:/static/images/DefaultAvatar.png");

            response.setContentType("image/png");
            if (person != null && person.getUsername() != null) {
                if (person.getUsername().equalsIgnoreCase("keibuy")) {
                    resource = resourceLoader.getResource("classpath:/static/images/adminAvatar.jpg");
                    response.setContentType("image/jpg");
                }
            }
            InputStream inputStream = resource.getInputStream();
            response.getOutputStream().write(FileCopyUtils.copyToByteArray(inputStream));
            response.getOutputStream().close();
        } else {
            byte[] decompressBytes = Utils.decompressImage(avatarImage.getBytes());
            response.setContentType(avatarImage.getContentType());
            response.getOutputStream().write(decompressBytes);
            response.getOutputStream().close();
        }
    }

    @GetMapping("/item/{id}")
    @ResponseBody
    void showItemImage(@PathVariable("id") Long id, HttpServletResponse response, ItemImage itemImage)
            throws IOException {
        itemImage = itemImageService.getImageById(id);
        if (itemImage == null) {
            Resource resource = resourceLoader.getResource("classpath:/static/images/logo.png");
            response.setContentType("image/png");
            InputStream inputStream = resource.getInputStream();
            response.getOutputStream().write(FileCopyUtils.copyToByteArray(inputStream));
            response.getOutputStream().close();
        } else {
            byte[] decompressBytes = Utils.decompressImage(itemImage.getBytes());
            response.setContentType(itemImage.getContentType());
            response.getOutputStream().write(decompressBytes);
            response.getOutputStream().close();
        }
    }
}
