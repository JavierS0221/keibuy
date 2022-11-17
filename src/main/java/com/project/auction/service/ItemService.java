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
    public long save(Item item);
    public long save(ItemDto itemDto);
    public void delete(Item item);
    public Item getItem(ItemDto itemDto);
    public Item getItem(Item item);

    Page<Item> findPaginated(int pageNo, int pageSize, String sortBy, boolean started, boolean notStarted, boolean virtualPayment, boolean physicalPayment, boolean excludeFinalized);

    public Item getItemById(long id);
    public void setFinalized(Item item, boolean finalized);
    public void sendEmails(Item item);
//    public int getLastItemId();
}
