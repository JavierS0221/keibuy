package com.project.auction.service;

import com.project.auction.model.Item;

import java.util.List;

public interface ItemService {
    public List<Item> listItems();
    public void save(Item item);
    public void delete(Item item);
    public Item getItem(Item item);
}
