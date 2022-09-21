package com.project.auction.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

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




    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }


    @Bean
    public CookieLocaleResolver localeResolver(){
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieName("my-locale-cookie");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }
}