package com.gibgab.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthenticationTestController {

    @RequestMapping("/secret/getmyusername")
    public @ResponseBody String getAuthenticatedUsername(Principal principal){
        String email = principal.getName();
        return email;
    }
}
