package com.project.auction.controller.auctions;

import com.project.auction.model.relation.AuctionOffer;
import com.project.auction.service.ItemService;
import com.project.auction.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AuctionsWSController {

    @Autowired
    PersonService personService;
    @Autowired
    ItemService itemService;
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/item/{id}/offer")
    @SendTo("/item/{id}/offer")
    public Response sendOffer(@DestinationVariable int id) {
        return new Response(null, 0, 0);
    }

//    @MessageMapping("/item/{id}/offer")
//    @SendTo("/item/{id}/offer")
//    public Response sendOffer(@DestinationVariable int id, AuctionWSObject auctionWSObject) throws InterruptedException {
//        // Response code:
//        // 0: Success
//        // 1: alreadyMostOfferError
//        // 2: lowerOfferError
//        // 3: personError
//        // 4: responseError
//        // 5: itemError
//
//        Thread.sleep(500);
//        Item item = itemService.getItemById(id);
//        if (item == null) return new Response(5, 0, 0, 0, 0);
//        AuctionOffer mostOffer = itemService.getMostOffer(item);
//
//        if (auctionWSObject == null) return new Response(4, 0, 0, mostOffer.getOffer(), mostOffer.getPerson().getId());
//
//        int personId = auctionWSObject.getPersonId();
//        Person person = null;
//
//        try {
//            person = personService.getPersonById(personId);
//        } catch (Exception ignored) {
//        }
//
//        if (person == null) return new Response(3, personId, 0, mostOffer.getOffer(), mostOffer.getPerson().getId());
//        int offer = auctionWSObject.getOffer();
//
//        if (mostOffer != null) {
//            if (mostOffer.getOffer() >= offer)
//                return new Response(2, personId, offer, mostOffer.getOffer(), mostOffer.getPerson().getId());
//            if (mostOffer.getPerson().getId() == personId) {
//                return new Response(1, personId, offer, mostOffer.getOffer(), mostOffer.getPerson().getId());
//            } else if (item.getParticipants().contains(person)) {
//                item.getAuctionOffers().remove(item.getOfferByPerson(person));
//            }
//        }
//
//        AuctionOffer auctionOffer = new AuctionOffer();
//        auctionOffer.setOffer(offer);
//        auctionOffer.setPerson(person);
//        auctionOffer.setItem(item);
//        item.getAuctionOffers().add(auctionOffer);
//        itemService.save(item);
//        return new Response(0, personId, offer, auctionOffer.getOffer(), auctionOffer.getPerson().getId());
//    }

    public void refreshOffer(AuctionOffer auctionOffer) {
        Response response = new Response(auctionOffer.getPerson().getUsername(), (int) auctionOffer.getPerson().getId(), auctionOffer.getOffer());
        this.template.convertAndSend("/item/" + auctionOffer.getItem().getId() + "/offer", response);
    }
}

//@Getter
//@Setter
//class AuctionWSObject {
//    private int personId;
//    private int offer;
//}

@Getter
@Setter
@AllArgsConstructor
class Response {
    private String username;
    private int personId;
    private int offer;
}
