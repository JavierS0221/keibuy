package com.project.auction.service;

import com.project.auction.dao.PersonDao;
import com.project.auction.dto.PersonDto;
import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> listPersons() {
        return (List<Person>) personDao.findAll();
    }

    @Override
    @Transactional
    public void save(PersonDto personDto) {
        Rol defaultRol = new Rol();
        defaultRol.setName("ROLE_USER");

        Person person = new Person();
        person.setUsername(personDto.getUsername());
        person.setName(personDto.getName());
        person.setLastName(personDto.getLastName());
        person.setPassword(passwordEncoder.encode(personDto.getPassword()));
        person.setEmail(personDto.getEmail());
        person.setPhone(personDto.getPhone());
        person.setBirthDate(personDto.getBirthDate());
        person.setAccountVerified(personDto.isAccountVerified());
        person.setRoles(List.of(defaultRol));
        personDao.save(person);
    }

    @Override
    @Transactional
    public void delete(PersonDto personDto) {
        Person person = this.getPerson(personDto);
        personDao.delete(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPerson(PersonDto personDto) {
        return personDao.findById(personDto.getId()).orElse(null);
    }
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personDao.findByUsername(username);

        if(person == null) {
            person = personDao.findByEmail(username);
        }

        if(person == null){
            throw new UsernameNotFoundException(username);
        }

        var roles = new ArrayList<GrantedAuthority>();

        for(Rol rol: person.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getName()));
        }

        return new User(person.getUsername(), person.getPassword(), roles);
    }
}
