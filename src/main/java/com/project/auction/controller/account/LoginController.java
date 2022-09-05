package com.project.auction.controller.account;

import com.project.auction.exception.AccountNotConfirmedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * Displays login page passing authentication errors
     *
     * @param request The request object.
     * @param model This is the model that will be passed to the view.
     * @return A String
     */
    @GetMapping()
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        boolean usernameNotFound = false;
        boolean accountNotConfirmed = false;

        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);


            if(ex != null) {
                errorMessage = ex.getMessage();

                String classSimpleName = ex.getClass().getSimpleName();
                String usernameNotFoundName = UsernameNotFoundException.class.getSimpleName();
                String accountNotConfirmedName = AccountNotConfirmedException.class.getSimpleName();


                if (classSimpleName.equals(usernameNotFoundName)) {
                    usernameNotFound = true;
                } else if (classSimpleName.equals(accountNotConfirmedName)) {
                    accountNotConfirmed = true;
                }
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("usernameNotFound", usernameNotFound);
        model.addAttribute("accountNotConfirmed", accountNotConfirmed);
        return "pages/login";
    }
}
