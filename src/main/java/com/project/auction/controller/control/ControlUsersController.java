package com.project.auction.controller.control;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.*;
import com.project.auction.model.Person;
import com.project.auction.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/control/users")
public class ControlUsersController {
    private final PersonService personService;

    private static final String REDIRECT_CONTROL = "redirect:/control/users";

    @Autowired
    public ControlUsersController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String users(@RequestParam(name = "page", defaultValue = "1") int pageNo,
                                @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                Model model) {
        int pageSize = 6;

        Page<Person> page = personService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Person> listPersons = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listPersons", listPersons);

        model.addAttribute("hasVerifyAccount", false);
        model.addAttribute("verifySuccess", true);

        return "control/users";
    }

    @GetMapping("/verify/{id}")
    public String verifyPerson(@PathVariable ( value = "id") long id, Model model, RedirectAttributes redirectAttr) {
        PersonDto personDto;
        try {
            personDto = personService.getPersonDtoById(id);
            personDto.setAccountVerified(true);
            personService.update(personDto);
        } catch (UnkownIdentifierException ex) {
            ex.printStackTrace();
            redirectAttr.addFlashAttribute("verifySuccess", false);
            return REDIRECT_CONTROL;
        }
        redirectAttr.addFlashAttribute("hasVerifyAccount", true);
        return REDIRECT_CONTROL;
    }

}
