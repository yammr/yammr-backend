package com.gibgab.service.controller;

import com.gibgab.service.account.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private Environment env;

    @PostMapping("/register")
    public @ResponseBody String register(@RequestBody User user){
        if(user.getEmail().matches(".*@.*.edu"))
            return "Good";
        else
            return "Bad";
//        throw new UnsupportedOperationException();
    }

    @RequestMapping("/sohel")
    public String index(){
        return env.getProperty("database.port");
    }
}
