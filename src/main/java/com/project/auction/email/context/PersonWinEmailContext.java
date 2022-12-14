package com.project.auction.email.context;

import com.project.auction.model.Item;
import com.project.auction.model.Person;
import org.springframework.web.util.UriComponentsBuilder;

public class PersonWinEmailContext extends AbstractEmailContext {

    @Override
    public <T, O> void init(T context, O obj){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        Item item = (Item) obj;
        Person person = (Person) context;
        put("item", item);
        put("person", person);
        put("auctioneerEmail", item.getPerson().getEmail());
        setTemplateLocation("emails/email-win");
        setSubject("Win auction");
        setFrom("no-reply@keibuy.com");
        setTo(person.getEmail());
    }

    public void buildItemUrl(final String baseURL, final long id){
        final String url = PersonWinEmailContext.getItemUrl(baseURL, id);
        put("itemURL", url);
    }

    public static String getItemUrl(final String baseURL, final long id) {
        return UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/item/"+id).toUriString();
    }

}