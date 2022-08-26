package com.project.auction.controller;

import com.project.auction.dao.PersonDao;
import com.project.auction.dto.PersonDto;
import com.project.auction.model.Person;
import com.project.auction.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {

    private PersonService personService;

    @Autowired
    public RegisterController(PersonService personService) {
        this.personService = personService;
    }

    @ModelAttribute("person")
    public PersonDto personDto() {
        return new PersonDto();
    }

    @GetMapping
    public String getRegisterForm() {
        return "pages/register";
    }

    @PostMapping
    public String registerPerson(@ModelAttribute("person") PersonDto personDto) {
        personService.save(personDto);
        return "redirect:/register?success";
    }
}
