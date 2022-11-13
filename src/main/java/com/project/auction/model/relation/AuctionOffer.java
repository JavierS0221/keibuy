package com.project.auction.model.relation;

import com.project.auction.model.Item;
import com.project.auction.model.Person;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "auction_offer")
@IdClass(AuctionOfferPK.class)
public class AuctionOffer implements Serializable {
    @Id
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "person_id")
    private Person person;
    @Id
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "offer")
    private int offer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuctionOffer that = (AuctionOffer) o;
        return person != null && Objects.equals(person, that.person)
                && item != null && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, item);
    }
}