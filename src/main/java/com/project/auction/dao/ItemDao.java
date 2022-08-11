package com.project.auction.dao;

import com.project.auction.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemDao extends CrudRepository<Item, Long> {

}
