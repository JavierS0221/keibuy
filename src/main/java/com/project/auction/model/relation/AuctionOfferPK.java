package com.project.auction.model.relation;

import com.project.auction.model.Item;
import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class AuctionOfferPK implements Serializable {

    private Person person;
    private Item item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionOfferPK that = (AuctionOfferPK) o;
        return person.equals(that.person) &&
                item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, item);
    }
}