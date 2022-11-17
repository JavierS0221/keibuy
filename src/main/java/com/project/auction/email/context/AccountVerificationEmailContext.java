package com.project.auction.email.context;

import com.project.auction.model.Person;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;

    @Override
    public <T> void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        Person customer = (Person) context; // we pass the customer informati
        put("username", customer.getUsername());
        setTemplateLocation("emails/email-verification");
        setSubject("Complete your registration");
        setFrom("no-reply@keibuy.com");
        setTo(customer.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url = AccountVerificationEmailContext.getVerificationUrl(baseURL, token);
        put("verificationURL", url);
    }

    public static String getVerificationUrl(final String baseURL, final String token) {
        return UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/register/verify").queryParam("token", token).toUriString();
    }
}