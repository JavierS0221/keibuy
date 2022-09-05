package com.project.auction.controller;

import com.project.auction.email.context.AccountVerificationEmailContext;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.Category;
import com.project.auction.model.Person;
import com.project.auction.model.SecureToken;
import com.project.auction.service.CategoryService;
import com.project.auction.service.PersonService;
import com.project.auction.service.SecureTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *  email controller
 *
 */
@Slf4j
@Controller
@RequestMapping("/email")
public class EmailController {

    @Value("${site.base.url.https}")
    private String baseURL;
    @Autowired
    private PersonService personService;
    @Autowired
    private SecureTokenService secureTokenService;

    @GetMapping
    public String email(@RequestParam(required = false, defaultValue = "{username}") String username, Model model) {
        Person person = null;
        String tokenCode = "";
        try {
            person = personService.getPersonByNameOrEmail(username);
        } catch (UnkownIdentifierException e) {
            e.printStackTrace();
        }
        if (person != null) {
            SecureToken token = secureTokenService.findByPersonId(person.getId());
            if (token != null)
                tokenCode = token.getToken();
        }
        model.addAttribute("verificationURL", AccountVerificationEmailContext.getVerificationUrl(baseURL, tokenCode));
        model.addAttribute("username", username);
        return "emails/email-verification";
    }
}
