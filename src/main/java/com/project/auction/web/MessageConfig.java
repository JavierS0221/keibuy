package com.project.auction.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageConfig implements WebMvcConfigurer {
//    @Autowired
//    MessageSource messageSource;
//
////    @Bean
////    public LocalValidatorFactoryBean getValidator() {
////        LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
////        validatorFactory.setValidationMessageSource(messageSource);
////        return validatorFactory;
////    }
//
//    // Alternativa a la declaracion en el application.yml
////	@Bean
////	public MessageSource messageSource() {
////		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
////		messageSource.setBasename("classpath:i18n/messages");
////		messageSource.setDefaultEncoding("UTF-8");
////		return messageSource;
////	}
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//        localeResolver.setDefaultLocale(Locale.getDefault());
//        return localeResolver;
//    }
//
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
//        localeInterceptor.setIgnoreInvalidLocale(true);
//        localeInterceptor.setParamName("lang");
//        return localeInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }

}