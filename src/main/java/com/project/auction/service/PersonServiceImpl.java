package com.project.auction.service;

import com.project.auction.email.context.AccountVerificationEmailContext;
import com.project.auction.exception.*;
import com.project.auction.model.AvatarImage;
import com.project.auction.model.SecureToken;
import com.project.auction.model.relation.PersonRol;
import com.project.auction.repository.PersonRepository;
import com.project.auction.dto.PersonDto;
import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {
    @Value("${site.base.url.https}")
    private String baseURL;

    @Value("${project.testing.mode}")
    private boolean projectTestingMode;

    @Resource
    private EmailService emailService;

    private PersonRepository personRepository;
    private RolService rolService;
    private AvatarImageService avatarImageService;
    private SecureTokenService secureTokenService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, RolService rolService, AvatarImageService avatarImageService, SecureTokenService secureTokenService, BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.rolService = rolService;
        this.avatarImageService = avatarImageService;
        this.secureTokenService = secureTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> listPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    @Transactional
    public void save(PersonDto personDto) throws UsernameAlreadyUsedException, EmailAlreadyUsedException, UsernameAndEmailAlreadyUsedException {
        if (checkIfPersonExistByUsername(personDto.getUsername())) {
            if (checkIfPersonExistByEmail(personDto.getEmail())) {
                throw new UsernameAndEmailAlreadyUsedException();
            }
            throw new UsernameAlreadyUsedException();
        }
        if (checkIfPersonExistByEmail(personDto.getEmail())) {
            throw new EmailAlreadyUsedException();
        }


        Person person = new Person();
        person.setUsername(personDto.getUsername());
        person.setName(personDto.getName());
        person.setLastName(personDto.getLastName());
        person.setPassword(passwordEncoder.encode(personDto.getPassword()));
        person.setEmail(personDto.getEmail());
        person.setPhone(personDto.getPhone());
        person.setCountry(personDto.getCountry());
        person.setBirthDate(personDto.getBirthDate());
        person.setCreatedDate(new Date());
        if (person.getUsername().equalsIgnoreCase("keibuy"))
            person.setAccountVerified(true);
        else
            person.setAccountVerified(personDto.isAccountVerified());
        person.setAccountBanned(personDto.isAccountBanned());
        person.setAvatar(personDto.getAvatar());

        String defaultRolName = "ROLE_USER";

        Rol defaultRol = rolService.getRol(defaultRolName);
        if (defaultRol == null) {
            defaultRol = new Rol();
            defaultRol.setName(defaultRolName);
        }
        person.addRol(defaultRol, null);
//
//        defaultRolName = "ROLE_ADMIN";
//
//        defaultRol = rolService.getRol(defaultRolName);
//        if (defaultRol == null) {
//            defaultRol = new Rol();
//            defaultRol.setName(defaultRolName);
//        }
//        person.addRol(defaultRol, null);

        personRepository.save(person);

        if (!person.getUsername().equalsIgnoreCase("keibuy"))
            sendRegistrationConfirmationEmail(person);
    }

    @Override
    @Transactional
    public void update(Person person) throws UnkownIdentifierException {
        if (person != null) {
            person.setUsername(person.getUsername());
            person.setName(person.getName());
            person.setLastName(person.getLastName());
            person.setPassword(person.getPassword());
            person.setEmail(person.getEmail());
            person.setPhone(person.getPhone());
            person.setCountry(person.getCountry());
            person.setBirthDate(person.getBirthDate());
            person.setCreatedDate(person.getCreatedDate());
            person.setAccountVerified(person.isAccountVerified());
            person.setAccountBanned(person.isAccountBanned());
            person.setRoles(person.getRoles());

            AvatarImage currentAvatar = avatarImageService.getImage(person.getAvatar());
            if (currentAvatar != null) {
                AvatarImage newAvatar = person.getAvatar();
                newAvatar.setId(currentAvatar.getId());
                avatarImageService.save(newAvatar);
            } else {
                person.setAvatar(person.getAvatar());
            }
            personRepository.save(person);
        }
    }

    @Override
    @Transactional
    public void update(PersonDto personDto) throws UnkownIdentifierException {
        Person person = this.getPerson(personDto);
        if (person != null) {
            person.setUsername(personDto.getUsername());
            person.setName(personDto.getName());
            person.setLastName(personDto.getLastName());
            person.setPassword(personDto.getPassword());
            person.setEmail(personDto.getEmail());
            person.setPhone(personDto.getPhone());
            person.setCountry(personDto.getCountry());
            person.setBirthDate(personDto.getBirthDate());
            person.setCreatedDate(personDto.getCreatedDate());
            person.setAccountVerified(personDto.isAccountVerified());
            person.setAccountBanned(personDto.isAccountBanned());
            person.setRoles(personDto.getRoles());

            AvatarImage currentAvatar = avatarImageService.getImage(person.getAvatar());
            if (currentAvatar != null) {
                AvatarImage newAvatar = personDto.getAvatar();
                newAvatar.setId(currentAvatar.getId());
                avatarImageService.save(newAvatar);
            } else {
                person.setAvatar(personDto.getAvatar());
            }
            personRepository.save(person);
        }
    }

    @Override
    @Transactional
    public void delete(PersonDto personDto) throws UnkownIdentifierException {
        Person person = this.getPerson(personDto);
        personRepository.delete(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPerson(PersonDto personDto) throws UnkownIdentifierException {
        Person person = personRepository.findById(personDto.getId()).orElse(null);
        if (person == null) {
            throw new UnkownIdentifierException("unable to find account");
        }
        return person;
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPersonById(long id) throws UnkownIdentifierException {
        Person person = personRepository.findById(id).orElse(null);
        if (person == null) {
            throw new UnkownIdentifierException("unable to find account");
        }
        return person;
    }

    @Override
    public PersonDto getPersonDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setUsername(person.getUsername());
        personDto.setName(person.getName());
        personDto.setLastName(person.getLastName());
        personDto.setEmail(person.getEmail());
        personDto.setCountry(person.getCountry());
        personDto.setPhone(person.getPhone());
        personDto.setPassword(person.getPassword());
        personDto.setBirthDate(person.getBirthDate());
        personDto.setCreatedDate(person.getCreatedDate());
        personDto.setAccountVerified(person.isAccountVerified());
        personDto.setAccountBanned(person.isAccountBanned());
        personDto.setRoles(person.getRoles());
        personDto.setAvatar(person.getAvatar());
        return personDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto getPersonDtoById(long id) throws UnkownIdentifierException {
        Person person = personRepository.findById(id).orElse(null);
        if (person == null) {
            throw new UnkownIdentifierException("unable to find account");
        }
        return this.getPersonDto(person);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto getPersonDtoByNameOrEmail(String nameOrEmail) throws UnkownIdentifierException {

        Person person = personRepository.findByUsername(nameOrEmail);
        if (person == null) {
            person = personRepository.findByEmail(nameOrEmail);
        }
        if (person == null) {
            throw new UnkownIdentifierException("unable to find account");
        }

        return this.getPersonDto(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPersonByNameOrEmail(String nameOrEmail) throws UnkownIdentifierException {
        Person person = personRepository.findByUsername(nameOrEmail);
        if (person == null) {
            person = personRepository.findByEmail(nameOrEmail);
        }
        if (person == null) {
            throw new UnkownIdentifierException("unable to find account");
        }
        return person;
    }

    @Override
    public boolean checkIfPersonExistByEmail(String email) {
        return personRepository.findByEmail(email) != null;
    }

    @Override
    public boolean checkIfPersonExistByUsername(String username) {
        return personRepository.findByUsername(username) != null;
    }

    @Override
    public void sendRegistrationConfirmationEmail(Person person) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setPerson(person);
        secureTokenService.saveSecureToken(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(person);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());

        if (!projectTestingMode) {
            try {
                emailService.sendMail(emailContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean verifyPerson(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
            throw new InvalidTokenException("Token is not valid");
        }

        Person person = personRepository.getReferenceById(secureToken.getPerson().getId());
        person.setAccountVerified(true);
        personRepository.save(person);
        secureTokenService.removeToken(secureToken);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);

        if (person == null) {
            person = personRepository.findByEmail(username);
        }

        if (person == null) {
            throw new UsernameNotFoundException("Not found account '" + username + "'");
        }

        var roles = new ArrayList<GrantedAuthority>();

        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        for (PersonRol personRol : person.getRoles()) {
            roles.add(new SimpleGrantedAuthority(personRol.getRol().getName()));
        }

        return new User(person.getUsername(), person.getPassword(), roles);
    }

    @Override
    public Page<Person> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.personRepository.findAll(pageable);
    }
}
