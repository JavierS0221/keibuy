package com.project.auction.repository;

import com.project.auction.model.relation.PersonRol;
import com.project.auction.model.relation.PersonRolPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRolRepository extends JpaRepository<PersonRol, PersonRolPK> {
}
