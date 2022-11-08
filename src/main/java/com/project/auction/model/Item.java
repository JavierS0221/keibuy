package com.project.auction.model;

import com.project.auction.model.relation.AuctionOffer;
import com.project.auction.model.relation.ItemImage;
import com.project.auction.model.relation.PersonRol;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "finish_date", nullable = false)
    private Date finishDate;

    @Column(name = "start_price")
    private int startPrice;

    @Column(name = "min_next_offer")
    private int minNextOffer;

    @Column(name = "status")
    private int status;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "location_id", nullable = false)
//    private Location locationId;

    @Column(name = "physical_payment")
    private boolean physicalPayment;

    @Column(name = "virtual_payment")
    private boolean virtualPayment;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<AuctionOffer> auctionOffers;

    @OneToMany(mappedBy = "image", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<ItemImage> itemImages;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//    @JoinTable(
//            name = "item_category",
//            joinColumns = @JoinColumn(
//                    name = "item_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
//
//    )
//    private Collection<Category> categories;

    public void setImages(List<Image> listImages) {
        List<ItemImage> listItemImage = new ArrayList<>();
        for (Image image : listImages) {
            ItemImage itemImage = new ItemImage();
            itemImage.setItem(this);
            itemImage.setImage(image);
            listItemImage.add(itemImage);
        }
        this.itemImages = listItemImage;
    }
}