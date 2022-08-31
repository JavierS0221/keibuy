package com.project.auction.controller;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.Category;
import com.project.auction.model.Person;
import com.project.auction.model.SecureToken;
import com.project.auction.service.CategoryService;
import com.project.auction.service.PersonService;
import com.project.auction.service.SecureTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        List<Category> listCategories = categoryService.listCategories();

        List<Category> listTopCategories = categoryService.listCategories();
        if(listTopCategories.size() > 10) {
            listTopCategories.subList(0, 11);
        }
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("listTopCategories", listTopCategories);
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
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
