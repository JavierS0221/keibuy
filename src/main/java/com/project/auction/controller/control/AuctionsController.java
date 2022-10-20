package com.project.auction.controller.control;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.Item;
import com.project.auction.model.Person;
import com.project.auction.service.ItemService;
import com.project.auction.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/auctions")
public class AuctionsController {
    private final ItemService itemService;
    private final PersonService personService;

    @Autowired
    public AuctionsController(ItemService itemService, PersonService personService) {
        this.itemService = itemService;
        this.personService = personService;
    }

    @GetMapping
    public String auctions(@RequestParam(name = "page", defaultValue = "1") int pageNo,
                                @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                Model model, @AuthenticationPrincipal User user) {
        int pageSize = 12;

        Page<Item> page = itemService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Item> listItems = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        Person person = null;
        if (user != null) {
            try {
                person = personService.getPersonByNameOrEmail(user.getUsername());
            } catch (UnkownIdentifierException ignored) {
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("person", person);

        model.addAttribute("listItems", listItems);
        return "auctions/listAuctions";
    }
}
