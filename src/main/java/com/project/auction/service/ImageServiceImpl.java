package com.project.auction.service;

import com.project.auction.model.AvatarImage;
import com.project.auction.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    @Transactional(readOnly = true)
    public AvatarImage getImageById(long id) {
        return imageRepository.findById(id).orElse(null);
    }
    @Override
    @Transactional(readOnly = true)
    public List<AvatarImage> listImages() {
        return (List<AvatarImage>) imageRepository.findAll();
    }

    @Override
    @Transactional
    public void save(AvatarImage avatarImage) {
        imageRepository.save(avatarImage);
    }

    @Override
    @Transactional
    public void delete(AvatarImage avatarImage) {
        imageRepository.delete(avatarImage);
    }

    @Override
    @Transactional(readOnly = true)
    public AvatarImage getImage(AvatarImage avatarImage) {
        if(avatarImage == null) return null;
        return imageRepository.findById(avatarImage.getId()).orElse(null);
    }
}
