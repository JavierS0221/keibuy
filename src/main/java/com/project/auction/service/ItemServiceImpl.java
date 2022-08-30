package com.project.auction.service;

import com.project.auction.repository.ItemRepository;
import com.project.auction.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Item> listItems() {
        return (List<Item>) itemRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    @Transactional
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItem(Item item) {
        return itemRepository.findById(item.getId()).orElse(null);
    }
}
