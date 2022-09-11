package com.project.auction.controller;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.EmailAlreadyExistException;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.exception.UsernameAlreadyExistException;
import com.project.auction.model.Category;
import com.project.auction.model.Person;
import com.project.auction.model.SecureToken;
import com.project.auction.repository.PersonRepository;
import com.project.auction.service.CategoryService;
import com.project.auction.service.PersonService;
import com.project.auction.service.SecureTokenService;
import com.project.auction.service.StorageService;
import com.project.auction.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PersonService personService;
    @Autowired
    private StorageService storageService;


    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal User user) {
        Person person = null;
        if(user != null) {
            try {
                person = personService.getPersonByNameOrEmail(user.getUsername());
            } catch (UnkownIdentifierException ignored) {
            }
        }
        List<Category> listCategories = categoryService.listCategories();

        List<Category> listTopCategories = categoryService.listCategories();
        if(listTopCategories.size() > 10) {
            listTopCategories.subList(0, 11);
        }
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("listTopCategories", listTopCategories);
        model.addAttribute("user", user);
        model.addAttribute("person", person);
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal User user) {
        Person person = null;
        if(user != null) {
            try {
                person = personService.getPersonByNameOrEmail(user.getUsername());
            } catch (UnkownIdentifierException ignored) {
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("person", person);
        return "pages/profile";
    }

//
//    @PostMapping("/register")
//    public String validRegister(@Validated Person person, Errors errors) {
//        if(errors.hasErrors()) {
//            return "pages/register";
//        }
//
//        personService.save(person);
//        return "redirect:/";
//    }

    @GetMapping("/forgot")
    public String forgot(Model model) {
        return "pages/forgot";
    }

    @GetMapping("/reset")
    public String reset(Model model) {
        return "pages/reset";
    }


    @GetMapping("/modify/{id}")
    public String editar(@PathVariable Integer id, Model model){
        PersonDto personDto = new PersonDto();
        try {
            personDto = personService.getPersonDto(id);
        } catch (UnkownIdentifierException e) {
            e.printStackTrace();
        }
        model.addAttribute("personDto", personDto);
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@Validated PersonDto personDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Existieron errores: "+ result.getAllErrors().toString());
            model.addAttribute("person", personDto);
            return "modify";
        }
        try {
        List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");
        if(!personDto.getAvatar().isEmpty()) {
            String contentType = personDto.getAvatar().getContentType();
            if(!contentTypes.contains(contentType)) {
                System.out.println("ERROR TIPO NO VALIDO");
                model.addAttribute("person", personDto);
                return "modify";
            }

            Person person = personService.getPerson(personDto);

            if(person.hasAvatar()) {
                storageService.deleteFile(person.getAvatarPath());
            }

            String path = personDto.getId()+"/avatar."+ personDto.getAvatar().getContentType().split("/")[1];
            System.out.println("path a usar:" + path);
            storageService.storeFile(personDto.getAvatar(), path);
        }

        personService.update(personDto);
        } catch (UnkownIdentifierException e) {
            System.out.println("ERROR FINAL");
            e.printStackTrace();
        }
        return "redirect:/";
    }










//    @GetMapping("/add")
//    public String add(User user) {
//
//        List<User> listUsers = userService.listPersons();
//        User reporter = listUsers.get(1);
//        User reported = listUsers.get(0);
//        Report report = new Report();
//        report.setType(1);
//        report.setMessage("test");
//        report.setReportedUser(reported);
//        reporter.addReport(report);
//
//        userService.save(reporter);
//        return "modify";
//    }
}
