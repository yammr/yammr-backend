package com.gibgab.service.controller;

import com.gibgab.service.database.User;
import com.gibgab.service.database.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Data
    private static class UserInfo {
        String email;
        String password;
    }

    @PostMapping("/register")
    public @ResponseBody String register(@RequestBody UserInfo user_info){
        if (user_info.getEmail().equals("") || user_info.getPassword().equals(""))
            return "Missing details";

        if(user_info.getEmail().matches(".*@.*.edu")) {
            if (userRepository.findByEmail(user_info.getEmail()) != null)
                return "User already exists";
            else {
                User new_user = new User();
                new_user.setEmail(user_info.getEmail());
                new_user.updatePassword(user_info.getPassword());

                userRepository.save(new_user);
                return "Created";
            }
        }
        else
            return "Bad";
    }

    @RequestMapping("/test")
    public String index(){
        return "I'm alive";
    }
}
