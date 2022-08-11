package com.project.auction.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username", length = 45, nullable = false)
    private String username;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "email", length = 80, nullable = false)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "permission_level")
    private int permissionLevel;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Report> reportsCreated;

    @OneToMany(mappedBy = "reportedUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Report> reportsReceived;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<AuctionOffer> auctionOffers;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Item> listItems;



    public void addReport(Report report) {
        if (this.reportsCreated == null) this.reportsCreated = new ArrayList<>();
        this.reportsCreated.add(report);
        report.setUser(this);
    }
}