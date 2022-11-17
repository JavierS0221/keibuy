package com.project.auction.repository;

import com.project.auction.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.name LIKE %?1% OR i.description LIKE %?1%")
    public List<Item> searchByKey(String key);
}
