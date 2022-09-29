package com.project.auction.model.relation;

import com.project.auction.constraints.BirthDate;
import com.project.auction.model.Item;
import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "person_rol")
public class PersonRol implements Serializable {

    @EmbeddedId
    private PersonRolPK id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("personId") //This is the name of attr in AuctionOfferPK class
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("rolId") //This is the name of attr in AuctionOfferPK class
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expire_date")
    private Date expireDate;
}