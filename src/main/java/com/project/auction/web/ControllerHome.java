package com.project.auction.web;

import com.project.auction.model.Category;
import com.project.auction.model.Report;
import com.project.auction.service.CategoryService;
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
