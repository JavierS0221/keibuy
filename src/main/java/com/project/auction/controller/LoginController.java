package com.project.auction.controller;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.EmailAlreadyExistException;
import com.project.auction.exception.InvalidTokenException;
import com.project.auction.exception.UsernameAlreadyExistException;
import com.project.auction.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login() {
        return "pages/login";
    }

}
