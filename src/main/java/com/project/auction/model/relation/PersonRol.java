package com.project.auction.model.relation;

import com.project.auction.constraints.BirthDate;
import com.project.auction.model.Item;
import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import com.project.auction.repository.PersonRepository;
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
@IdClass(PersonRolPK.class)
public class PersonRol implements Serializable {
    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "person_id")
    private Person person;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "rol_id")
    private Rol rol;


    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expire_date")
    private Date expireDate;
}