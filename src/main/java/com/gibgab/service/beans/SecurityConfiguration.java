package com.gibgab.service.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfiguration {

    @Value("#{'${pages.public.get}'.split(',')}")
    public String[] publicAccessGetPages;

    @Value("#{'${pages.public.post}'.split(',')}")
    public String[] publicAccessPostPages;

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
}
