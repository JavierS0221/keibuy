package com.project.auction.web;

import com.project.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserService userService;
    private AuthenticationConfiguration configuration;

    public SecurityConfig(UserService userDetailsService, AuthenticationConfiguration configuration) {
        super();
        this.userService = userDetailsService;
        this.configuration = configuration;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/profile")
                .hasRole("USER")
                .antMatchers("/", "/profile/**", "register")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login").permitAll().loginProcessingUrl("/login")
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/errors/403");
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService).passwordEncoder(getPasswordEncoder());
    }
}
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//            .authorizeRequests()
//                .antMatchers("/editar/**", "/agregar/**", "/eliminar")
//                .hasRole("ADMIN")
//                .antMatchers("/")
//                .permitAll()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .and()
//            .logout()
//                .permitAll()
//                .and()
//            .exceptionHandling().accessDeniedPage("/errores/403");
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService)
//    {
//        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(ldapContextSource);
//
//        UserDetailsServiceLdapAuthoritiesPopulator ldapAuthoritiesPopulator = new UserDetailsServiceLdapAuthoritiesPopulator(userDetailsService);
//
//        factory.setUserSearchFilter("(uid={0})");
//        factory.setUserSearchBase("ou=people");
//        factory.setLdapAuthoritiesPopulator(ldapAuthoritiesPopulator);
//
//        return factory.createAuthenticationManager();
//    }
//
//
//
////    @Autowired
////    private UserDetailsService userDetailsService;
////
////    @Bean
////    public BCryptPasswordEncoder passwordEncoder(){
////        return new BCryptPasswordEncoder();
////    }
//
//    @Bean
//    protected  configureAuthentication() {
////        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//
//
//    }
//
//
////        http.authorizeRequests()
////                .antMatchers("/", "/img/**", "/js/**", "/css/**").permitAll();
////    }
//}
