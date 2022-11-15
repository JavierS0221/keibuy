package com.project.auction.controller.auctions;

import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.Item;
import com.project.auction.model.Person;
import com.project.auction.model.relation.AuctionOffer;
import com.project.auction.service.ItemService;
import com.project.auction.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String offer(@PathVariable("id") Long id, @AuthenticationPrincipal User user, @RequestParam(name = "offer") int offer, Model model, RedirectAttributes redirectAttributes) {

        boolean success = false;
        boolean alreadyIsMostOffer = false;
        boolean lowerOffer = false;
        boolean itemNotFound = false;
        boolean accountNotFound = false;


        if (user != null) {
            try {
                Person person = personService.getPersonByNameOrEmail(user.getUsername());
                Item item = itemService.getItemById(id);

//                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                if (!passwordEncoder.matches(password, person.getPassword())) {
//                    return "redirect:/item/" + id + "?error";
//                }

                if (item != null) {

                    AuctionOffer newAuctionOffer = new AuctionOffer();

                    int mostOffer;
                    AuctionOffer auctionOffer = item.getMostOffer();
                    if (auctionOffer != null) {
                        mostOffer = auctionOffer.getOffer();

                        if (auctionOffer.getPerson() == person) {
                            alreadyIsMostOffer = true;
                        } else {
                            if ((mostOffer + item.getMinNextOffer()) > offer) {
                                lowerOffer = true;
                            } else {
                                newAuctionOffer.setPerson(person);
                                newAuctionOffer.setItem(item);
                                newAuctionOffer.setOffer(offer);

                                item.getAuctionOffers().remove(newAuctionOffer);
                                item.getAuctionOffers().add(newAuctionOffer);

                                itemService.save(item);
                                auctionsWSController.refreshOffer(newAuctionOffer);
                                success = true;
                            }
                        }
                    } else {
                        if (item.getStartPrice() <= offer) {
                            newAuctionOffer.setPerson(person);
                            newAuctionOffer.setItem(item);
                            newAuctionOffer.setOffer(offer);

                            item.getAuctionOffers().add(newAuctionOffer);

                            itemService.save(item);
                            auctionsWSController.refreshOffer(newAuctionOffer);
                            success = true;
                        } else {
                            lowerOffer = true;
                        }
                    }
                } else {
                    itemNotFound = true;
                }
            } catch (
                    UnkownIdentifierException e) {
                e.printStackTrace();
            }
        } else {
            accountNotFound = true;
        }


        redirectAttributes.addFlashAttribute("alreadyIsMostOffer", alreadyIsMostOffer);
        redirectAttributes.addFlashAttribute("lowerOffer", lowerOffer);
        redirectAttributes.addFlashAttribute("itemNotFound", itemNotFound);
        redirectAttributes.addFlashAttribute("accountNotFound", accountNotFound);
        redirectAttributes.addFlashAttribute("success", success);

        return "redirect:/item/" + id;
    }

}
