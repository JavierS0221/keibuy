package com.project.auction.web;

import com.project.auction.model.Report;
import com.project.auction.model.User;
import com.project.auction.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ControllerHome {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {

        List<User> listUsers = userService.listPersons();
        log.info("cantidad: " + listUsers.size());
        log.info("Ejecutando el controlador rest");
        model.addAttribute("listUsers", listUsers);
        return "index";
    }

    @GetMapping("/add")
    public String add(User user) {

        List<User> listUsers = userService.listPersons();
        Report report = new Report();

        User newUser = new User();
        newUser.setUsername("j3");
        newUser.setName("hola2");
        newUser.setLastName("adios3");
        newUser.setEmail("holaa12dios@gmail.com");
        newUser.setPhone("998884552");


        report.setType(1);
        report.setMessage("test");
        report.setReportedUserId(listUsers.get(0).getId());

        List<Report> listReports = newUser.getReportsCreated();
        if(listReports == null) listReports = new ArrayList<Report>();
        listReports.add(report);
        newUser.setReportsCreated(listReports);

        userService.save(newUser);
        return "modify";
    }
}
