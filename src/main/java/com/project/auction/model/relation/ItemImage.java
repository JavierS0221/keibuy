package com.project.auction.model.relation;

import com.project.auction.model.Image;
import com.project.auction.model.Item;
import com.project.auction.model.Person;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "item_image")
public class ItemImage implements Serializable {

    @EmbeddedId
    private ItemImagePK id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("itemId") //This is the name of attr in AuctionOfferPK class
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("imageId") //This is the name of attr in AuctionOfferPK class
    @JoinColumn(name = "image_id")
    private Image image;
}