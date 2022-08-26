package com.project.auction.dao;

import com.project.auction.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonDao extends CrudRepository<Person, Long> {
    Person findByUsername(String username);
    Person findByEmail(String email);
}
