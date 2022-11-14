package com.project.auction.service;

import com.project.auction.dto.ItemDto;
import com.project.auction.model.Item;
import com.project.auction.model.ItemImage;
import com.project.auction.model.Person;
import com.project.auction.model.relation.AuctionOffer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {
    public List<Item> listItems();
    public void save(Item item);
    public void save(ItemDto itemDto);
    public void delete(Item item);
    public Item getItem(ItemDto itemDto);
    public Item getItem(Item item);
    public Page<Item> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    public Item getItemById(long id);
    public AuctionOffer getMostOffer(Item item);
}
