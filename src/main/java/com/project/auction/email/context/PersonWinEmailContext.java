package com.project.auction.email.context;

import com.project.auction.model.Item;
import com.project.auction.model.Person;
import org.springframework.web.util.UriComponentsBuilder;

public class PersonWinEmailContext extends AbstractEmailContext {

    @Override
    public <T> void init(T context, T itemContext){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        Item item = (Item) itemContext;
        Person person = (Person) ;
        put("id", item.getId());
        put("name", item.getName());
        put("auctioneerEmail", item.getPerson().getEmail());
        setTemplateLocation("emails/email-win");
        setSubject("Complete your registration");
        setFrom("no-reply@keibuy.com");
        setTo(customer.getEmail());
    }

}