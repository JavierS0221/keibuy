package com.project.auction.service;

import com.project.auction.dto.ItemDto;
import com.project.auction.model.ItemImage;
import com.project.auction.repository.ItemRepository;
import com.project.auction.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public void save(ItemDto itemDto) {
        Item item = getItem(itemDto);
        if (item != null)
            itemRepository.save(item);
    }

    @Override
    @Transactional
    public void update(Item item) {
        Item registerItem = this.getItem(item);
        if (registerItem != null) {
            registerItem.setName(item.getName());
            registerItem.setDescription(item.getDescription());
            registerItem.setPerson(item.getPerson());
            registerItem.setItemImages(item.getItemImages());
            registerItem.setAuctionOffers(item.getAuctionOffers());
            registerItem.setStartDate(item.getStartDate());
            registerItem.setFinishDate(item.getFinishDate());
//            registerItem.setCategories(item.getCategories());
            registerItem.setMinNextOffer(item.getMinNextOffer());
            registerItem.setStartPrice(item.getStartPrice());
//            registerItem.setLocationId(item.getLocationId());
            registerItem.setPhysicalPayment(item.isPhysicalPayment());
            registerItem.setVirtualPayment(item.isVirtualPayment());
            registerItem.setFinalized(item.isFinalized());
            itemRepository.save(item);
        }
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

    public Item getItem(ItemDto itemDto) {
        if (itemDto == null) return null;
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPerson(itemDto.getPerson());
        item.setImages(itemDto.getItemImages());
        item.setAuctionOffers(itemDto.getAuctionOffers());
        item.setStartDate(itemDto.getStartDate());
        item.setFinishDate(itemDto.getFinishDate());
//        item.setCategories(itemDto.getCategories());
        item.setMinNextOffer(itemDto.getMinNextOffer());
        item.setStartPrice(itemDto.getStartPrice());
//        item.setLocationId(itemDto.getLocationId());
        item.setPhysicalPayment(itemDto.isPhysicalPayment());
        item.setVirtualPayment(itemDto.isVirtualPayment());
        item.setFinalized(itemDto.isFinalized());
        return item;
    }

    @Override
    public Page<Item> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.itemRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItemById(long id) {
        return itemRepository.findById(id).orElse(null);
    }
}
