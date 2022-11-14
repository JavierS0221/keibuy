package com.project.auction.service;

import com.project.auction.model.AvatarImage;
import com.project.auction.repository.AvatarImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AvatarImageServiceImpl implements AvatarImageService {

    @Autowired
    private AvatarImageRepository avatarImageRepository;
    @Override
    @Transactional(readOnly = true)
    public AvatarImage getImageById(long id) {
        return avatarImageRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvatarImage> listImages() {
        return (List<AvatarImage>) avatarImageRepository.findAll();
    }

    @Override
    @Transactional
    public void save(AvatarImage avatarImage) {
        avatarImageRepository.save(avatarImage);
    }

    @Override
    @Transactional
    public void delete(AvatarImage avatarImage) {
        avatarImageRepository.delete(avatarImage);
    }

    @Override
    @Transactional(readOnly = true)
    public AvatarImage getImage(AvatarImage avatarImage) {
        if(avatarImage == null) return null;
        return avatarImageRepository.findById(avatarImage.getId()).orElse(null);
    }
}
