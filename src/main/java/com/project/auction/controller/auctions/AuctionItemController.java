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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
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
import org.springframework.web.socket.messaging.WebSocketStompClient;

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
    private final AuctionsWSController auctionsWSController;

    @Autowired
    public AuctionItemController(ItemService itemService, PersonService personService, AuctionsWSController auctionsWSController) {
        this.itemService = itemService;
        this.personService = personService;
        this.auctionsWSController = auctionsWSController;
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
        model.addAttribute("item", item);
        if (item != null) {
            return "pages/itemPage";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}/offer")
    public String offer(@PathVariable("id") Long id, @AuthenticationPrincipal User user, @RequestParam(name = "offer") int offer, @RequestParam(name = "password") String password, Model model) {
        if (user != null) {
            try {
                Person person = personService.getPersonByNameOrEmail(user.getUsername());
                Item item = itemService.getItemById(id);

                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (!passwordEncoder.matches(password, person.getPassword())) {
                    return "redirect:/item/" + id + "?error";
                }

                if (item != null) {

                    int mostOffer = -1;
                    AuctionOffer auctionOffer = item.getMostOffer();
                    if (auctionOffer != null) {
                        mostOffer = auctionOffer.getOffer();
                    }
                    if (mostOffer < offer) {
                        AuctionOffer newAuctionOffer = new AuctionOffer();
                        newAuctionOffer.setPerson(person);
                        newAuctionOffer.setItem(item);
                        newAuctionOffer.setOffer(offer);

                        item.getAuctionOffers().remove(newAuctionOffer);
                        item.getAuctionOffers().add(newAuctionOffer);

                        itemService.save(item);
                        auctionsWSController.refreshOffer(newAuctionOffer);
                        return "redirect:/item/" + id + "?success";
                    } else {
                        return "redirect:/item/" + id + "?error";
                    }

                }
            } catch (UnkownIdentifierException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/item/" + id;
    }

}
