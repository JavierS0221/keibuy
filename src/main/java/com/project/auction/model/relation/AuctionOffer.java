package com.project.auction.model.relation;

import com.project.auction.model.Item;
import com.project.auction.model.Person;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "auction_offer")
public class AuctionOffer implements Serializable {

    @EmbeddedId
    private AuctionOfferPK id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("personId") //This is the name of attr in AuctionOfferPK class
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("itemId") //This is the name of attr in AuctionOfferPK class
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "offer")
    private int offer;
}