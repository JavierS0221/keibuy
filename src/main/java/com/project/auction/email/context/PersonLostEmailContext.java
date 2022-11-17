package com.project.auction.email.context;

import com.project.auction.model.Item;
import com.project.auction.model.Person;
import com.project.auction.model.relation.AuctionOffer;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class PersonLostEmailContext extends AbstractEmailContext {

    @Override
    public <T, O> void init(T context, O obj) {
        //we can do any common configuration setup here
        // like setting up some base URL and context
        Item item = (Item) obj;
        Person person = (Person) context;

        int position = 0;
        List<AuctionOffer> offerList = item.getOffersInOrder();
        for (int i = 0; i < offerList.size(); i++) {
            AuctionOffer auctionOffer = offerList.get(i);
            if (auctionOffer.getPerson().equals(person)) {
                position = (i + 1);
                break;
            }
        }

        put("item", item);
        put("person", person);
        put("auctioneerEmail", item.getPerson().getEmail());
        put("position", position);
        setTemplateLocation("emails/email-lost");
        setSubject("Lost auction");
        setFrom("no-reply@keibuy.com");
        setTo(person.getEmail());
    }

    public void buildItemUrl(final String baseURL, final long id){
        final String url = PersonLostEmailContext.getItemUrl(baseURL, id);
        put("itemURL", url);
    }

    public static String getItemUrl(final String baseURL, final long id) {
        return UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/item/"+id).toUriString();
    }

}