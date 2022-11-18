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
import org.springframework.data.repository.query.Param;
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
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("/myauctions")
public class MyAuctionsController {
    private final ItemService itemService;
    private final PersonService personService;

    @Autowired
    public MyAuctionsController(ItemService itemService, PersonService personService) {
        this.itemService = itemService;
        this.personService = personService;
    }

    @GetMapping
    public String auctions(Model model, @AuthenticationPrincipal User user) {

        Person person = null;
        if (user != null) {
            try {
                person = personService.getPersonByNameOrEmail(user.getUsername());
            } catch (UnkownIdentifierException ignored) {
            }
        }


        int pageNo = 1;
        String searchKey = null;
        String sortByFilter = "most_recent";
        boolean startedFilter = true;
        boolean notStartedFilter = true;
        boolean virtualPaymentFilter = true;
        boolean physicalPaymentFilter = true;

        Object objSearchKey = model.getAttribute("searchKey");
        Object objPageNo = model.getAttribute("currentPage");
        Object objSortByFilter = model.getAttribute("sortByFilter");
        Object objStartedFilter = model.getAttribute("startedFilter");
        Object objNotStartedFilter = model.getAttribute("notStartedFilter");
        Object objVirtualPaymentFilter = model.getAttribute("virtualPaymentFilter");
        Object objPhysicalPaymentFilter = model.getAttribute("physicalPaymentFilter");

        if (objSearchKey != null) {
            try {
                searchKey = (String) objSearchKey;
            } catch (Exception ignored) {
            }
        }

        if (objPageNo != null) {
            pageNo = (int) objPageNo;
        }

        if (objSortByFilter != null) {
            sortByFilter = (String) objSortByFilter;
        }


        if (objStartedFilter != null) {
            try {
                startedFilter = (boolean) objStartedFilter;
            } catch (Exception ignored) {
            }
        }
        if (objNotStartedFilter != null) {
            try {
                notStartedFilter = (boolean) objNotStartedFilter;
            } catch (Exception ignored) {
            }
        }
        if (objVirtualPaymentFilter != null) {
            try {
                virtualPaymentFilter = (boolean) objVirtualPaymentFilter;
            } catch (Exception ignored) {
            }
        }
        if (objPhysicalPaymentFilter != null) {
            try {
                physicalPaymentFilter = (boolean) objPhysicalPaymentFilter;
            } catch (Exception ignored) {
            }
        }

        int pageSize = 12;
        Page<Item> page = itemService.findPaginated(person, pageNo, pageSize, sortByFilter, startedFilter, notStartedFilter, virtualPaymentFilter, physicalPaymentFilter, searchKey, true);
        List<Item> listItems = page.getContent();
        if (pageNo > page.getTotalPages()) pageNo = page.getTotalPages();


        int minViewPage = pageNo - 2;
        if (minViewPage < 1) minViewPage = 1;

        int maxViewPage = pageNo + 2;
        if (maxViewPage > page.getTotalPages()) maxViewPage = page.getTotalPages();

        if (maxViewPage - minViewPage < 4) minViewPage -= 1;
        if (maxViewPage - minViewPage < 4) minViewPage -= 1;
        if (minViewPage < 1) minViewPage = 1;

        if (maxViewPage - minViewPage < 4) maxViewPage += 1;
        if (maxViewPage - minViewPage < 4) maxViewPage += 1;
        if (maxViewPage > page.getTotalPages()) maxViewPage = page.getTotalPages();


        model.addAttribute("minViewPage", minViewPage);
        model.addAttribute("maxViewPage", maxViewPage);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());


        model.addAttribute("user", user);
        model.addAttribute("person", person);

        model.addAttribute("listItems", listItems);


        model.addAttribute("sortByFilter", sortByFilter);
        model.addAttribute("startedFilter", startedFilter);
        model.addAttribute("notStartedFilter", notStartedFilter);
        model.addAttribute("virtualPaymentFilter", virtualPaymentFilter);
        model.addAttribute("physicalPaymentFilter", physicalPaymentFilter);

        return "auctions/listMyAuctions";
    }

    @PostMapping()
    public String auctionsFilter(@RequestParam(name = "a1") Optional<String> a1, @RequestParam(name = "b1") Optional<String> b1, @RequestParam(name = "b2") Optional<String> b2, @RequestParam(name = "c1") Optional<String> c1, @RequestParam(name = "c2") Optional<String> c2, Model model, RedirectAttributes redirectAttributes) {
        String sortByFilter = (a1.orElse(null));
        boolean startedFilter = (b1.map(s -> (s.equals("1"))).orElse(false));
        boolean notStartedFilter = (b2.map(s -> (s.equals("1"))).orElse(false));
        boolean virtualPaymentFilter = (c1.map(s -> (s.equals("1"))).orElse(false));
        boolean physicalPaymentFilter = (c2.map(s -> (s.equals("1"))).orElse(false));

        redirectAttributes.addFlashAttribute("sortByFilter", sortByFilter);
        redirectAttributes.addFlashAttribute("startedFilter", startedFilter);
        redirectAttributes.addFlashAttribute("notStartedFilter", notStartedFilter);
        redirectAttributes.addFlashAttribute("virtualPaymentFilter", virtualPaymentFilter);
        redirectAttributes.addFlashAttribute("physicalPaymentFilter", physicalPaymentFilter);
        return "redirect:/myauctions";
    }

    @PostMapping("/search")
    public String auctionsSearch(@Param("search") String search, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("searchKey", search);
        return "redirect:/myauctions";
    }

    @GetMapping("/reset")
    public String auctionsFilterReset(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("sortByFilter", null);
        redirectAttributes.addFlashAttribute("startedFilter", 0);
        redirectAttributes.addFlashAttribute("notStartedFilter", 0);
        redirectAttributes.addFlashAttribute("virtualPaymentFilter", 0);
        redirectAttributes.addFlashAttribute("physicalPaymentFilter", 0);
        return "redirect:/myauctions";
    }

    @GetMapping("{pageNo}")
    public String auctionsFilterReset(@PathVariable int pageNo, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("currentPage", pageNo);
        return "redirect:/myauctions";
    }
}

