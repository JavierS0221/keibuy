package com.project.auction.repository;

import com.project.auction.model.Rol;
import com.project.auction.model.relation.PersonRolPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByName(String name);
}
