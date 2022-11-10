package com.project.auction.dto;

import com.project.auction.model.ItemImage;
import com.project.auction.model.Person;
import com.project.auction.model.relation.AuctionOffer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Data
public class ItemDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String description;
    private Person person;
    @Future(message = "The start date must be in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Future(message = "The finish date must be in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;
    private int startPrice;
    private int minNextOffer;
    private int status;
//    private Location locationId;
    private boolean physicalPayment;
    private boolean virtualPayment;
    private List<AuctionOffer> auctionOffers;
    private Collection<ItemImage> itemImages = new HashSet<>();
//    private Collection<Category> categories;
}