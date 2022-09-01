package com.project.auction.security.authentication;

import com.project.auction.exception.AccountNotConfirmedException;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.Person;
import com.project.auction.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    PersonService personService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // Perform the checks from the super class
        super.additionalAuthenticationChecks(userDetails, authentication);

        // Cast the UserDetails to the implementation you use
        Person person = null;
        try {
            person = personService.getPersonByNameOrEmail(userDetails.getUsername());
        } catch (UnkownIdentifierException e) {
            throw new UsernameNotFoundException("Account not exist");
        }

        // Check the confirmed status
        if (!person.isAccountVerified()) {
            throw new AccountNotConfirmedException("Account is not confirmed yet.");
        }
    }

}