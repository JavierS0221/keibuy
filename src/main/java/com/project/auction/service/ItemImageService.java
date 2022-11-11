package com.project.auction.service;

import com.project.auction.model.ItemImage;

import java.util.List;

public interface ItemImageService {
    public List<ItemImage> listImages();
    public void save(ItemImage itemImage);
    public void delete(ItemImage itemImage);
    public ItemImage getImage(ItemImage itemImage);
    public ItemImage getImageById(long id);
}
