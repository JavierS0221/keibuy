package com.project.auction.service;

import java.util.List;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.EmailAlreadyExistException;
import com.project.auction.exception.InvalidTokenException;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.exception.UsernameAlreadyExistException;
import com.project.auction.model.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PersonService extends UserDetailsService{
    public List<Person> listPersons();
    public void save(PersonDto personDto) throws UsernameAlreadyExistException, EmailAlreadyExistException;
    public void update(PersonDto personDto) throws UnkownIdentifierException;
    public void delete(PersonDto personDto) throws UnkownIdentifierException;
    public Person getPerson(PersonDto personDto) throws UnkownIdentifierException;
    public PersonDto getPersonDto(long id) throws UnkownIdentifierException;
    public Person getPersonById(long id) throws UnkownIdentifierException;
    public Person getPersonByNameOrEmail(String nameOrEmail) throws UnkownIdentifierException;
    public boolean checkIfPersonExistByEmail(String email);
    public boolean checkIfPersonExistByUsername(String username);
    public void sendRegistrationConfirmationEmail(Person person);
    public boolean verifyPerson(String token) throws InvalidTokenException;
}
