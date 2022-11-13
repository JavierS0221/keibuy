package com.project.auction.controller.auctions;

import com.project.auction.dto.ItemDto;
import com.project.auction.dto.PersonDto;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.Item;
import com.project.auction.model.ItemImage;
import com.project.auction.model.Person;
import com.project.auction.service.ItemService;
import com.project.auction.service.PersonService;
import com.project.auction.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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


    @ModelAttribute("itemDto")
    public ItemDto itemDto() {
        return new ItemDto();
    }

    @GetMapping("/new")
    public String newAuction(Model model, @AuthenticationPrincipal User user, @Valid ItemDto itemDto, BindingResult bindingResult, RedirectAttributes rm) {
        if (bindingResult.hasErrors()) {
            return "auctions/newAuction";
        }

        Person person = null;
        PersonDto personDto = new PersonDto();
        if (user != null) {
            try {
                person = personService.getPersonByNameOrEmail(user.getUsername());
                personDto = personService.getPersonDto(person);
            } catch (UnkownIdentifierException ignored) {
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("person", person);
        model.addAttribute("personDto", personDto);

        return "auctions/newAuction";
    }

    @PostMapping("/new")
    public String newAuctionPost(Model model, @AuthenticationPrincipal User user, @Valid ItemDto itemDto, BindingResult bindingResult, RedirectAttributes rm, @RequestParam("files") MultipartFile[] files) {
        if (bindingResult.hasErrors()) {
            return "auctions/newAuction";
        }

        Person person = null;
        if (user != null) {
            try {
                person = personService.getPersonByNameOrEmail(user.getUsername());
            } catch (UnkownIdentifierException ignored) {
            }
        }

        if(person == null) return "auctions/newAuction";


        try {
            List<ItemImage> itemImages = new ArrayList<>();

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String contentType = file.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        return "redirect:/auctions/new";
                    }

                    String fileName = file.getOriginalFilename();
                    if (fileName == null || fileName.contains("..")) {
                        model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                        return "redirect:/auctions/new";
                    }

                    ItemImage itemImage = new ItemImage();

                    itemImage.setContentType(file.getContentType());
                    itemImage.setFileName(fileName);
                    itemImage.setBytes(Utils.compressImage(file.getBytes()));
                    itemImages.add(itemImage);
                }
            }
            itemDto.setItemImages(itemImages);
            itemDto.setPerson(person);
            itemDto.setFinalized(false);
            itemService.save(itemDto);
            return "redirect:/auctions/new?success";
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "auctions/newAuction";
    }
}
