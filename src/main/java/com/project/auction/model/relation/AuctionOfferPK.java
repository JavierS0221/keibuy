package com.project.auction.model.relation;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class AuctionOfferPK implements Serializable {

    @Column(name = "person_id")
    private long personId;

    @Column(name = "item_id")
    private long itemId;

}