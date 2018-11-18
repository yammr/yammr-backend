package com.gibgab.service.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfiguration {

    @Value("#{'${pages.public.get}'.split(',')}")
    private String[] publicAccessGetPages;

    @Value("#{'${pages.public.post}'.split(',')}")
    private String[] publicAccessPostPages;

    @Value("#{'${pages.secure.moderator}'.split(',')}")
    private String[] moderatorPages;

    @Value("#{'${pages.secure.user}'.split(',')}")
    private String[] userPages;


    @Value("#{'${security.role.moderator}'}")
    private String moderatorRole;

    @Value("#{'${security.role.user}'}")
    private String userRole;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public String[] publicAccessGetPages(){
        return publicAccessGetPages;
    }

    @Bean
    public String[] publicAccessPostPages(){
        return publicAccessPostPages;
    }

    @Bean
    public String[] moderatorPages(){
        return moderatorPages;
    }

    @Bean
    public String[] userPages(){
        return userPages;
    }

    @Bean
    public String moderatorRole(){
        return moderatorRole;
    }

    @Bean
    public String userRole(){
        return userRole;
    }
}
