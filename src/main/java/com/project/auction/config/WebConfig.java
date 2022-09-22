package com.project.auction.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

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
        registry.addViewController("/error/403").setViewName("/error/403");
        registry.addViewController("/error/404").setViewName("/error/404");
        registry.addViewController("/items").setViewName("/pages/itemFilter");
        registry.addViewController("/chat-test").setViewName("/layout/chat.component");
        if(projectTestingMode) {
            registry.addViewController("/email").setViewName("/emails/email-verification");
        }

    }

}
