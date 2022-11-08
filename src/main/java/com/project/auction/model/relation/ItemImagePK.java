package com.project.auction.model.relation;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ItemImagePK implements Serializable {

    @Column(name = "item_id")
    private long itemId;

    @Column(name = "image_id")
    private long imageId;

}