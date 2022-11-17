package com.project.auction.service;

import com.project.auction.dto.ItemDto;
import com.project.auction.email.context.*;
import com.project.auction.model.Person;
import com.project.auction.model.relation.AuctionOffer;
import com.project.auction.repository.ItemRepository;
import com.project.auction.model.Item;
import com.project.auction.util.AuctionOffersExporterExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Value("${project.testing.mode}")
    private boolean projectTestingMode;
    @Resource
    private EmailService emailService;

    @Value("${site.base.url.https}")
    private String baseURL;

    @Override
    @Transactional(readOnly = true)
    public List<Item> listItems() {
        return (List<Item>) itemRepository.findAll();
    }

    @Override
    @Transactional
    public long save(Item item) {
        return itemRepository.save(item).getId();
    }

    @Override
    @Transactional
    public long save(ItemDto itemDto) {
        Item item = getItem(itemDto);
        if (item != null) {
            return itemRepository.save(item).getId();

        }
        return 0;
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
    public Page<Item> findPaginated(int pageNo, int pageSize, String sortBy, boolean started, boolean notStarted, boolean virtualPayment, boolean physicalPayment, String searchKey, boolean excludeFinalized) {
        List<Item> itemList = this.listItems();
        if(searchKey != null) {
            System.out.println("no es null");
            System.out.println("search: "+searchKey);
            itemList = this.searchByKey(searchKey);
        } else {

            System.out.println("null");
        }

        if (started && !notStarted)
            itemList = itemList.stream().filter(Item::isEnabled).collect(Collectors.toList());
        else if (!started && notStarted) {
            itemList = itemList.stream().filter(Item::isInStandby).collect(Collectors.toList());
        }

        if (virtualPayment && !physicalPayment)
            itemList = itemList.stream().filter(Item::isVirtualPayment).collect(Collectors.toList());
        else if (!virtualPayment && physicalPayment) {
            itemList = itemList.stream().filter(Item::isPhysicalPayment).collect(Collectors.toList());
        }

        Comparator<Item> itemComparator = switch (sortBy) {
            case "less_recent" -> (Comparator.comparingLong((Item o) -> o.getStartDate().getTime()));
            case "highest_offer" ->
                    ((o1, o2) -> Integer.compare((o2.getMostOffer() != null ? o2.getMostOffer().getOffer() : o2.getStartPrice()), (o1.getMostOffer() != null ? o1.getMostOffer().getOffer() :  o1.getStartPrice())));
            case "lower_offer" ->
                    (Comparator.comparingInt((Item o) -> (o.getMostOffer() != null ? o.getMostOffer().getOffer() : o.getStartPrice())));
            default -> ((o1, o2) -> Long.compare(o2.getStartDate().getTime(), o1.getStartDate().getTime()));
        };
        itemList.sort(itemComparator);

        if(excludeFinalized) {
            itemList = itemList.stream().filter(item -> !item.isFinalized()).collect(Collectors.toList());
        }

        int start = (int) PageRequest.of(pageNo-1, pageSize).getOffset();
        int end = Math.min((start + PageRequest.of(pageNo-1, pageSize).getPageSize()), itemList.size());
        List<Item> newList;
        try {
            newList = itemList.subList(start, end);
        } catch (Exception ignored) {
            pageNo = 1;
            start = (int) PageRequest.of((0), pageSize).getOffset();
            end = Math.min((start + PageRequest.of((0), pageSize).getPageSize()), itemList.size());
            newList = itemList.subList(start, end);
        }
        return new PageImpl<>(newList, PageRequest.of(pageNo-1, pageSize), itemList.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItemById(long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void setFinalized(Item item, boolean finalized) {
        item = this.getItem(item);
        if (item != null) {
            item.setFinalized(finalized);
            itemRepository.save(item);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public void sendEmails(Item item) {
        item = this.getItem(item);
        if (item != null) {
            AuctionOffer mostOffer = item.getMostOffer();

            Person autor = item.getPerson();
            if (autor != null) {
                if (mostOffer != null) {
                    AuctioneerParticipantsEmailContext emailContext = new AuctioneerParticipantsEmailContext();
                    emailContext.init(autor, item);
                    emailContext.buildItemUrl(baseURL, item.getId());
                    if (!projectTestingMode) {
                        try {
                            AuctionOffersExporterExcel exporter = new AuctionOffersExporterExcel(item);
                            emailService.sendMail(emailContext, "Auction-" + item.getId() + ".xlsx", exporter.export());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    AuctioneerEmailContext emailContext = new AuctioneerEmailContext();
                    emailContext.init(item.getPerson(), item);
                    emailContext.buildItemUrl(baseURL, item.getId());
                    if (!projectTestingMode) {
                        try {
                            emailService.sendMail(emailContext);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (mostOffer != null) {

                Person personWin = mostOffer.getPerson();

                for (Person person : item.getParticipants()) {
                    if (person == personWin) continue;

                    PersonLostEmailContext emailContext = new PersonLostEmailContext();
                    emailContext.init(person, item);
                    emailContext.buildItemUrl(baseURL, item.getId());

                    if (!projectTestingMode) {
                        try {
                            emailService.sendMail(emailContext);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                PersonWinEmailContext emailContext = new PersonWinEmailContext();
                emailContext.init(personWin, item);
                emailContext.buildItemUrl(baseURL, item.getId());

                if (!projectTestingMode) {
                    try {
                        emailService.sendMail(emailContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> searchByKey(String key) {
        List<Item> list = new ArrayList<>();
        if(key != null) {
            list = itemRepository.searchByKey(key);
        }
        return list;
    }
}
