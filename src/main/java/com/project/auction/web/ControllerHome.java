package com.project.auction.web;

import com.project.auction.model.Category;
import com.project.auction.model.Report;
import com.project.auction.model.Person;
import com.project.auction.model.Rol;
import com.project.auction.service.CategoryService;
import com.project.auction.service.PersonService;
import com.project.auction.util.EncriptPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ControllerHome {

    @Autowired
    private PersonService personService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model, Person person){
        List<Category> listCategories = categoryService.listCategories();

        List<Category> listTopCategories = categoryService.listCategories();
        if(listTopCategories.size() > 10) {
            listTopCategories.subList(0, 11);
        }
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("listTopCategories", listTopCategories);

//        Person newPerson = new Person();
//        newPerson.setUsername("Juan777");
//        newPerson.setPassword(EncriptPassword.encript("123"));
//        newPerson.setName("Juan");
//        newPerson.setLastName("Perez");
//        newPerson.setEmail("juanperez777@gmail.com");
//        newPerson.setBirthDate(new Date());
//        newPerson.setPhone("092152712");
//        Rol rol = new Rol();
//        rol.setPerson(newPerson);
//        rol.setName("USER");
//
//        List<Rol> roles = new ArrayList<>();
//        roles.add(rol);
//
//        newPerson.setRoles(roles);
//        personService.save(newPerson);

        return "index";
    }

    @GetMapping("/add")
    public String add(Person person) {

        List<Person> listPersons = personService.listPersons();
        Person reporter = listPersons.get(1);
        Person reported = listPersons.get(0);
        Report report = new Report();
        report.setType(1);
        report.setMessage("test");
        report.setReportedPerson(reported);
        reporter.addReport(report);

        personService.save(reporter);
        return "modify";
    }

    @GetMapping("/profile/{idPerson}")
    public String profile(Person person, Model model){
        person = personService.getPerson(person);
        model.addAttribute("person", person);
        return "pages/profile";
    }
}
