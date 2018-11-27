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

    @Value("#{'${pages.secure.moderator}'.split(',')}")
    public String[] moderatorPages;

    @Value("#{'${pages.secure.user}'.split(',')}")
    public String[] userPages;


    @Value("#{'${security.role.moderator}'}")
    public String moderatorRole;

    @Value("#{'${security.role.user}'}")
    public String userRole;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
