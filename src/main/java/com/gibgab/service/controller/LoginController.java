package com.gibgab.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import com.gibgab.service.database.User;
import com.gibgab.service.database.UserRepository;
import lombok.*;

@RestController
public class LoginController {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Data
    private static class UserInfo {
        String email;
        String password;
    }

    @PostMapping("/login")
    public @ResponseBody String register(@RequestBody UserInfo user_info){
        if (user_info.getEmail().equals("") || user_info.getPassword().equals(""))
            return "Missing details";

        User user = userRepository.findByEmail(user_info.email);

        if (user == null) {
            return "Invalid Login";
        }

        if (user.verifyPassword(user_info.password)) {
            return "you'll get a jwt eventually";
        }
        else {
            return "Invalid Login";
        }
    }
}
