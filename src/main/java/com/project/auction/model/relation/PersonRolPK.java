package com.project.auction.model.relation;

import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class PersonRolPK implements Serializable {

    @OneToOne(optional = false)
    @JoinColumn(name = "person_id")
    private Person personId;

    @OneToOne(optional = false)
    @JoinColumn(name = "rol_id")
    private Rol rolId;

}