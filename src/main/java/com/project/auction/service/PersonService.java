package com.project.auction.service;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.*;
import com.project.auction.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PersonService extends UserDetailsService {
    public List<Person> listPersons();

    public void save(PersonDto personDto) throws UsernameAlreadyUsedException, EmailAlreadyUsedException, UsernameAndEmailAlreadyUsedException;

    public void update(PersonDto personDto) throws UnkownIdentifierException;

    public void update(Person person) throws UnkownIdentifierException;

    public void delete(PersonDto personDto) throws UnkownIdentifierException;

    public Person getPerson(PersonDto personDto) throws UnkownIdentifierException;

    public PersonDto getPersonDto(Person person);

    public PersonDto getPersonDtoById(long id) throws UnkownIdentifierException;

    public PersonDto getPersonDtoByNameOrEmail(String nameOrEmail) throws UnkownIdentifierException;

    public Person getPersonById(long id) throws UnkownIdentifierException;

    public Person getPersonByNameOrEmail(String nameOrEmail) throws UnkownIdentifierException;

    public boolean checkIfPersonExistByEmail(String email);

    public boolean checkIfPersonExistByUsername(String username);

    public void sendRegistrationConfirmationEmail(Person person);

    public boolean verifyPerson(String token) throws InvalidTokenException;

    public Page<Person> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
