package com.project.auction.service;

import java.util.List;

import com.project.auction.dto.PersonDto;
import com.project.auction.model.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PersonService extends UserDetailsService{
    public List<Person> listPersons();
    public void save(PersonDto personDto);
    public void delete(PersonDto personDto);
    public Person getPerson(PersonDto personDto);
}
