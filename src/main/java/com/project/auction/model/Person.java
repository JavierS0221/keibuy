package com.project.auction.model;

import com.project.auction.model.relation.AuctionOffer;
import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"}))
public class Person {

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "person_rol",
            joinColumns = @JoinColumn(
                    name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")

    )
    private Collection<Rol> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "auction_wished",
            joinColumns = @JoinColumn(
                    name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id")

    )
    private Collection<Item> auctionsWished;

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


    public boolean hasAvatar() {
        File f = new File("assets\\" + this.id);
        File[] files = f.listFiles((dir1, name) -> name.startsWith("avatar"));
        if (files != null) {
            List<File> avatars = Arrays.stream(files).toList();
            return !avatars.isEmpty();
        } else {
            return false;
        }
    }

    public String getAvatarPath() {
//        System.out.println(Paths.get("assets").resolve(id+"").toString());
        File f = new File("assets\\" + this.id);

        File[] files = f.listFiles((dir1, name) -> name.startsWith("avatar"));
        String path = null;
        if (files != null) {
            List<File> avatars = Arrays.stream(files).toList();
            if (!avatars.isEmpty()) {
                path = avatars.get(0).getPath();
                path = path.substring(path.indexOf("\\") + 1).replace("\\", "/");
            }
        }
        return path;
    }
}