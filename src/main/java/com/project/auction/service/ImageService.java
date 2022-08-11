package com.project.auction.service;

import com.project.auction.model.Image;

import java.util.List;

public interface ImageService {
    public List<Image> listImages();
    public void save(Image image);
    public void delete(Image image);
    public Image getImage(Image image);
}
