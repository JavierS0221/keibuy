package com.project.auction.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "auction_wished")
public class AuctionWished implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="item_id", nullable = false)
    private Item item;
}