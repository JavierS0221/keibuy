package com.project.auction.model;

import com.project.auction.model.relation.PersonRol;
import lombok.Data;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "rol", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
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

    @Column(name = "color")
    private String color = "#6b6b6b";


    @OneToMany(
            mappedBy = "rol",
            cascade = CascadeType.ALL
    )
    private Collection<PersonRol> persons = new ArrayList<>();

    public String getNameWihoutFormat() {
        if (this.name.length() <= 5) return this.name;
        String firstLatter = this.name.substring(5, 6).toUpperCase();
        return firstLatter + this.name.substring(6).toLowerCase();
    }
}