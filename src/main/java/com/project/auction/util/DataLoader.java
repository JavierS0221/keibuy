package com.project.auction.util;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.EmailAlreadyUsedException;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.exception.UsernameAlreadyUsedException;
import com.project.auction.exception.UsernameAndEmailAlreadyUsedException;
import com.project.auction.model.AvatarImage;
import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import com.project.auction.repository.RolRepository;
import com.project.auction.service.PersonService;
import com.project.auction.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final RolService rolService;
    private final PersonService personService;

    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    public DataLoader(RolService rolService, PersonService personService) {
        this.rolService = rolService;
        this.personService = personService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        this.initRoles();
        this.initAdmin();
    }

    private void initAdmin() {
        if (personService == null) return;

        PersonDto personDto = null;
        Person person = null;
        try {
            person = personService.getPersonByNameOrEmail("keibuy");
        } catch (UnkownIdentifierException ignored) {
        }

        if (person == null) {
            personDto = new PersonDto();
            personDto.setUsername("keibuy");
            personDto.setName("keibuy");
            personDto.setLastName("auctions");
            personDto.setEmail(email);
            personDto.setAccountVerified(true);
            personDto.setBirthDate(new Date(1));
            personDto.setPassword("keibuy1234");
            personDto.setPhone("-");
            personDto.setCountry("-");
            try {
                personService.save(personDto);
                person = personService.getPersonByNameOrEmail("keibuy");
            } catch (UnkownIdentifierException | UsernameAlreadyUsedException | UsernameAndEmailAlreadyUsedException |
                     EmailAlreadyUsedException ignored) {
            }

            if (person != null) {
                String adminRolName = "ROLE_ADMIN";
                Rol adminRol = rolService.getRol(adminRolName);
                if (adminRol == null) {
                    adminRol = new Rol();
                    adminRol.setName(adminRolName);
                }
                person.addRol(adminRol, null);

                try {
                    personService.update(person);
                } catch (UnkownIdentifierException ignored) {
                }
            }

        }
    }

    private void initRoles() {

        if (rolService == null) return;
        try {
            Yaml yaml = new Yaml();
            InputStreamReader inputStream = new InputStreamReader(new ClassPathResource("roles.yml", this.getClass().getClassLoader()).getInputStream());
            Map<String, Map<String, Object>> obj = yaml.load(inputStream);

            for (String key : obj.keySet()) {
                Rol rol = rolService.getRol(key);
                if (rol == null) {
                    rol = new Rol();
                    rol.setName(key);
                }
                int priority = (int) obj.get(key).get("priority");
                boolean hasExpire = (boolean) obj.get(key).get("has-expire");

                String color = "000";
                if (obj.get(key).containsKey("color")) color = (String) obj.get(key).get("color");

                rol.setPriority(priority);
                rol.setHasExpire(hasExpire);
                rol.setColor(color);
                rolService.save(rol);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}