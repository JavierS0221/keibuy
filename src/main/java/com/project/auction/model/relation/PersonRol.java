package com.project.auction.model.relation;

import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PersonRol personRol = (PersonRol) o;
        return person != null && Objects.equals(person, personRol.person)
                && rol != null && Objects.equals(rol, personRol.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, rol);
    }
}