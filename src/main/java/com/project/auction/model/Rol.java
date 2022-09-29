package com.project.auction.model;

import com.project.auction.model.relation.PersonRol;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name = "rol")
public class Rol implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "has_expire")
    private boolean hasExpire;

    @Column(name = "priority")
    private int priority = 0;

    @OneToMany(
            mappedBy = "rol",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Collection<PersonRol> persons;
}