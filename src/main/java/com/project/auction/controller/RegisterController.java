package com.project.auction.controller;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.EmailAlreadyExistException;
import com.project.auction.exception.InvalidTokenException;
import com.project.auction.exception.UsernameAlreadyExistException;
import com.project.auction.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;


@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Value("${project.testing.mode}")
    private boolean projectTestingMode;

    private static final String REDIRECT_LOGIN = "redirect:/login";
    @Resource
    private MessageSource messageSource;

    private final PersonService personService;

    @Autowired
    public RegisterController(PersonService personService) {
        this.personService = personService;
    }

    @ModelAttribute("person")
    public PersonDto personDto() {
        return new PersonDto();
    }

    @GetMapping
    public String register() {
        return "pages/register";
    }

    @PostMapping
    public String registerPerson(@ModelAttribute("person") PersonDto personDto, BindingResult bindingResult, Model model,RedirectAttributes rm) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", personDto);
            return "pages/register";
        }
        try {
            personService.save(personDto);

            if (projectTestingMode) {
                rm.addAttribute("username", personDto.getUsername());
                return "redirect:/email";
            } else {
                return "redirect:/register?success";
            }
        } catch (UsernameAlreadyExistException | EmailAlreadyExistException ex) {
            bindingResult.rejectValue("email", "userData.email", ex.getMessage());
            model.addAttribute("registrationForm", personDto);
            return "pages/register";
        }
    }

    @GetMapping("/verify")
    public String verifyPerson(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr) {
        if (StringUtils.isEmpty(token)) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        try {
            personService.verifyPerson(token);
        } catch (InvalidTokenException ex) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.invalid.token", null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        redirAttr.addFlashAttribute("verifiedAccountMsg", messageSource.getMessage("user.registration.verification.success", null, LocaleContextHolder.getLocale()));
        return REDIRECT_LOGIN;
    }

}
