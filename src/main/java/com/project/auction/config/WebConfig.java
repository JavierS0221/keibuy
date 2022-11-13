package com.project.auction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Value("${project.testing.mode}")
    private boolean projectTestingMode;

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("/pages/login");
        registry.addViewController("/register").setViewName("/pages/register");
        registry.addViewController("/forgot").setViewName("/pages/forgot");
        registry.addViewController("/reset").setViewName("/pages/reset");
        registry.addViewController("/profile").setViewName("/pages/profile");
        registry.addViewController("/manual").setViewName("/pages/manual");
        registry.addViewController("/error/403").setViewName("/error/403");
        registry.addViewController("/error/404").setViewName("/error/404");
        registry.addViewController("/chat-test").setViewName("/layout/chat");
        registry.addViewController("/control").setViewName("/control/dashboard");
        registry.addViewController("/control/users").setViewName("/control/users");
        registry.addViewController("/auctions").setViewName("/auctions/listAuctions");
        registry.addViewController("/auctions/new").setViewName("/auctions/newAuction");
        if(projectTestingMode) {
            registry.addViewController("/email").setViewName("/emails/email-verification");
        }

    }

}
