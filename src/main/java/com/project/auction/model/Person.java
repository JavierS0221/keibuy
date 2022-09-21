package com.project.auction.model;

import com.project.auction.constraints.BirthDate;
import com.project.auction.model.relation.AuctionOffer;
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
    @NotEmpty(message="El usuario es obligatorio")
    @Size(min = 2, max = 32, message = "El usuario debe tener entre 2 y 32 caracteres")
    private String username;

    @NotEmpty(message="El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede tener más de 45 caracteres")
    @Column(name = "name")
    private String name;

    @NotEmpty(message="El apellido es obligatorio")
    @Size(max = 45, message = "El apellido no puede tener más de 45 caracteres")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message="La contraseña es obligatoria")
    @Column(name = "password")
    private String password;

    @NotEmpty(message="El email es obligatorio")
    @Email(message = "Email no válido")
    @Column(name = "email")
    private String email;

    @NotEmpty(message="El pais es obligatorio")
    @Column(name = "country")
    private String country;

    @NotEmpty(message="El teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono no puede tener más de 15 dígitos")
    @Column(name = "phone")
    private String phone;

    @NotNull(message = "The birth date is required.")
    @BirthDate(message = "The birth date must be greater or equal than 18")
    @Past(message = "The birth date must be in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "account_verified")
    private boolean accountVerified = false;

    @Column(name = "account_banned")
    private boolean accountBanned = false;

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