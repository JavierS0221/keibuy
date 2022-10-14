package com.project.auction.service;

import com.project.auction.model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    public List<Image> listImages();
    public void save(Image image);
    public void delete(Image image);
    public Image getImage(Image image);
    public Image getImageById(long id);
}
