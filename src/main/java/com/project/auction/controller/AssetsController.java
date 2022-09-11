package com.project.auction.controller;

import com.project.auction.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/assets/**")
public class AssetsController {

    @Autowired
    private StorageService storageService;

    @GetMapping
    public Resource getResource(HttpServletRequest request) {
        String path = request.getRequestURI();
        path = path.substring(path.indexOf("/") + 1);
        path = path.substring(path.indexOf("/") + 1);
        return storageService.getResource(path);
    }
}
