package com.project.auction.service;

import com.project.auction.email.context.AccountVerificationEmailContext;
import com.project.auction.model.Item;
import com.project.auction.model.ItemImage;
import com.project.auction.model.Person;
import com.project.auction.model.SecureToken;
import com.project.auction.repository.ItemImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemImageServiceImpl implements ItemImageService {

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Override
    @Transactional(readOnly = true)
    public ItemImage getImageById(long id) {
        return itemImageRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemImage> listImages() {
        return itemImageRepository.findAll();
    }

    @Override
    @Transactional
    public void save(ItemImage itemImage) {
        itemImageRepository.save(itemImage);
    }

    @Override
    @Transactional
    public void delete(ItemImage itemImage) {
        itemImageRepository.delete(itemImage);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemImage getImage(ItemImage itemImage) {
        if (itemImage == null) return null;
        return itemImageRepository.findById(itemImage.getId()).orElse(null);
    }

}
