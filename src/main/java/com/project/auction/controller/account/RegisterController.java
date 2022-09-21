package com.project.auction.controller.account;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.EmailAlreadyUsedException;
import com.project.auction.exception.InvalidTokenException;
import com.project.auction.exception.UsernameAlreadyUsedException;
import com.project.auction.exception.UsernameAndEmailAlreadyUsedException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;


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

    @ModelAttribute("personDto")
    public PersonDto personDto() {
        return new PersonDto();
    }

    @GetMapping
    public String register() {
        return "pages/register";
    }

    @PostMapping
    public String registerPerson(@Valid PersonDto personDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes rm) {

        if(bindingResult.hasErrors()){
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
        } catch (UsernameAndEmailAlreadyUsedException ex) {
            bindingResult.rejectValue("username", "usernameAlreadyExist", "Username already used");
            bindingResult.rejectValue("email", "userData.email", "Email already used");
        } catch (UsernameAlreadyUsedException ex) {
            bindingResult.rejectValue("username", "usernameAlreadyExist", "Username already used");
        } catch (EmailAlreadyUsedException ex) {
            bindingResult.rejectValue("email", "userData.email", "Email already used");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "pages/register";
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
