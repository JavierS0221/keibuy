package com.project.auction.service;

import com.project.auction.dao.ImageDao;
import com.project.auction.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    @Transactional(readOnly = true)
    public List<Image> listImages() {
        return (List<Image>) imageDao.findAll();
    }

    @Override
    @Transactional
    public void save(Image image) {
        imageDao.save(image);
    }

    @Override
    @Transactional
    public void delete(Image image) {
        imageDao.delete(image);
    }

    @Override
    @Transactional(readOnly = true)
    public Image getImage(Image image) {
        return imageDao.findById(image.getFileName()).orElse(null);
    }
}
