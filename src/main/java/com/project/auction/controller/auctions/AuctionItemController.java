package com.project.auction.controller.auctions;

import com.project.auction.dto.ItemDto;
import com.project.auction.dto.PersonDto;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.AvatarImage;
import com.project.auction.model.Item;
import com.project.auction.model.ItemImage;
import com.project.auction.model.Person;
import com.project.auction.model.relation.AuctionOffer;
import com.project.auction.model.relation.AuctionOfferPK;
import com.project.auction.service.ItemService;
import com.project.auction.service.PersonService;
import com.project.auction.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/item")
public class AuctionItemController {
    private final ItemService itemService;
    private final PersonService personService;

    @Autowired
    public AuctionItemController(ItemService itemService, PersonService personService) {
        this.itemService = itemService;
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
        Item item = itemService.getItemById(id);
        Person person = null;
        if (user != null) {
            try {
                person = personService.getPersonByNameOrEmail(user.getUsername());
            } catch (UnkownIdentifierException ignored) {
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("person", person);
        if (item != null) {
            model.addAttribute("item", item);
            return "pages/itemPage";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}/offer")
    public String offer(@PathVariable("id") Long id, @AuthenticationPrincipal User user, @RequestParam(name = "offer") int offer, @RequestParam(name = "password") String password, Model model) {
        System.out.println("x");
        if (user != null) {
            try {
                System.out.println("1");
                Person person = personService.getPersonByNameOrEmail(user.getUsername());
                Item item = itemService.getItemById(id);

                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                System.out.println("A '"+password+"'");
                System.out.println("B '"+person.getPassword()+"'");
                System.out.println("C '"+!(passwordEncoder.matches(password, person.getPassword()))+"'");
                if(!passwordEncoder.matches(password, person.getPassword())) {
                    System.out.println("Error contraseña");
                    return "redirect:/item/" + id + "?error";
                }

                System.out.println("2");
                if (item != null) {

                    System.out.println("3");
                    int mostOffer = -1;
                    AuctionOffer auctionOffer = item.getMostOffer();
                    if (auctionOffer != null) {
                        mostOffer = auctionOffer.getOffer();
                        System.out.println("asfas");
                    }
                    System.out.println("-3");
                    if (mostOffer < offer) {
                        AuctionOffer newAuctionOffer = new AuctionOffer();
                        newAuctionOffer.setPerson(person);
                        newAuctionOffer.setItem(item);
                        newAuctionOffer.setOffer(offer);
                        System.out.println("0001");

                        item.getAuctionOffers().remove(newAuctionOffer);
                        item.getAuctionOffers().add(newAuctionOffer);

                        System.out.println("0002");
                        itemService.save(item);
                        return "redirect:/item/" + id + "?success";
                    } else {
                        return "redirect:/item/" + id + "?error";
                    }

                }
            } catch (UnkownIdentifierException e) {
                System.out.println("4");
                e.printStackTrace();
            }
            System.out.println("5");
        }

        System.out.println("6");
        return "redirect:/item/" + id;
    }

}
