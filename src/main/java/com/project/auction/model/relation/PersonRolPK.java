package com.project.auction.model.relation;

import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import lombok.Data;

import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.Objects;

@Data
public class PersonRolPK implements Serializable {

    private Person person;
    private Rol rol;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRolPK that = (PersonRolPK) o;
        return person.equals(that.person) &&
                rol.equals(that.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, rol);
    }
}