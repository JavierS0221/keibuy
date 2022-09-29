package com.project.auction.model.relation;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PersonRolPK implements Serializable {

    @Column(name = "person_id")
    private long personId;

    @Column(name = "rol_id")
    private long rolId;

}