package com.project.auction.model;

import com.project.auction.constraints.BirthDate;
import com.project.auction.model.relation.AuctionOffer;
import com.project.auction.model.relation.PersonRol;
import com.project.auction.model.relation.PersonRolPK;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @Column(name = "username")
    @NotEmpty
    @Size(min = 2, max = 32)
    private String username;

    @NotEmpty
    @Size(max = 45)
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Size(max = 45)
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty
    @Column(name = "country")
    private String country;

    @NotEmpty
    @Size(max = 15)
    @Column(name = "phone")
    private String phone;

    @NotNull
    @BirthDate
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private Date birthDate;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "account_verified")
    private boolean accountVerified = false;

    @Column(name = "account_banned")
    private boolean accountBanned = false;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<Report> reportsCreated;

    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Collection<PersonRol> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "auction_wished",
            joinColumns = @JoinColumn(
                    name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id")

    )
    private Collection<Item> auctionsWished;

    @OneToMany(mappedBy = "reportedPerson", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    private Collection<Report> reportsReceived;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    private Collection<AuctionOffer> auctionOffers;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    private Collection<Item> items;



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

    public void addRol(Rol rol, Date expireDate) {
        PersonRolPK pRolPK = new PersonRolPK();
        pRolPK.setPersonId(this);
        pRolPK.setRolId(rol);

        PersonRol personRol = new PersonRol();
        personRol.setId(pRolPK);
        personRol.setExpireDate(expireDate);
        roles.add(personRol);
    }

    public void removeRol(Rol rol) {
        for (Iterator<PersonRol> iterator = roles.iterator();
             iterator.hasNext(); ) {
            PersonRol personRol = iterator.next();

            if (personRol.getPerson().getUsername().equals(this.getUsername()) &&
                    personRol.getRol().getName().equals(rol.getName())) {
                iterator.remove();
                personRol.setPerson(null);
                personRol.setRol(null);
            }
        }
    }

    public PersonRol getMainRol() {
        List<PersonRol> ordRoles = (List<PersonRol>) this.roles;

        // Sort employees by Salary
        Comparator<PersonRol> rolPriorityComparator = (e1, e2) -> {
            if (e1.getRol().getPriority() < e2.getRol().getPriority()) {
                return -1;
            } else if (e1.getRol().getPriority() > e2.getRol().getPriority()) {
                return 1;
            } else {
                return 0;
            }
        };
        Collections.sort(ordRoles, rolPriorityComparator);
        for(int i = 0; i < ordRoles.size(); i++) {
            System.out.println(i + " " + ordRoles.get(i).getRol().getName());
        }
        return ordRoles.get(0);
    }
}