package com.project.auction.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "location")
public class Location implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "location", length = 45, nullable = false)
    private String location;

    @Column(name = "delivery_locations", length = 2000, nullable = false)
    private String deliveryLocations;

    @Column(name = "place_retreat", length = 250, nullable = false)
    private String placeRetreat;

}