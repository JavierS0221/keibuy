package com.project.auction.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = {"username","email"}))
public class Person  {

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

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "email", length = 80, nullable = false)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "birth_date")
    private Date birthDate;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<Report> reportsCreated;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_rol",
            joinColumns =  @JoinColumn(
                    name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")

    )
    private Collection<Rol> roles;

    @OneToMany(mappedBy = "reportedPerson", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<Report> reportsReceived;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<AuctionOffer> auctionOffers;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<Item> items;


    private boolean accountVerified;

    public void addReport(Report report) {
        if (this.reportsCreated == null) this.reportsCreated = new ArrayList<>();
        this.reportsCreated.add(report);
        report.setPerson(this);
    }
}