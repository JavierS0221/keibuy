package com.project.auction.service;

import com.project.auction.dao.ItemDao;
import com.project.auction.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    @Transactional(readOnly = true)
    public List<Item> listItems() {
        return (List<Item>) itemDao.findAll();
    }

    @Override
    @Transactional
    public void save(Item item) {
        itemDao.save(item);
    }

    @Override
    @Transactional
    public void delete(Item item) {
        itemDao.delete(item);
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItem(Item item) {
        return itemDao.findById(item.getId()).orElse(null);
    }
}
