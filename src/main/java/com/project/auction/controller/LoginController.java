package com.project.auction.controller;

import com.project.auction.security.authentication.CustomAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

//    @GetMapping
//    public String login() {
//
//        System.out.println("Test 1");
//        return "pages/login";
//    }
    @GetMapping()
    public String loginError(HttpServletRequest request, Model model) {
        System.out.println("Test 2");
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//            System.out.println(ex.getClass().getSimpleName());
//            System.out.println(CustomAuthenticationProvider.AccountNotConfirmedException.class.getSimpleName().equals(ex.getClass().getSimpleName()));
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "pages/login";
    }
}
