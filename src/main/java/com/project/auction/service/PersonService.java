package com.project.auction.service;

import java.util.List;
import com.project.auction.model.Person;

public interface PersonService {
    public List<Person> listPersons();
    public void save(Person person);
    public void delete(Person person);
    public Person getPerson(Person person);
}
