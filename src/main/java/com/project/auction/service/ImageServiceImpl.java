package com.project.auction.service;

import com.project.auction.repository.ImageRepository;
import com.project.auction.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    @Transactional(readOnly = true)
    public Image getImageById(long id) {
        return imageRepository.findById(id).orElse(null);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Image> listImages() {
        return (List<Image>) imageRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    @Transactional
    public void delete(Image image) {
        imageRepository.delete(image);
    }

    @Override
    @Transactional(readOnly = true)
    public Image getImage(Image image) {
        if(image == null) return null;
        return imageRepository.findById(image.getId()).orElse(null);
    }
}
