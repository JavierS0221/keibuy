package com.project.auction.service;

import com.project.auction.model.AvatarImage;

import java.util.List;

public interface AvatarImageService {
    public List<AvatarImage> listImages();
    public void save(AvatarImage avatarImage);
    public void delete(AvatarImage avatarImage);
    public AvatarImage getImage(AvatarImage avatarImage);
    public AvatarImage getImageById(long id);
}
