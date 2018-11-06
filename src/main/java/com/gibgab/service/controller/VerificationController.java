package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.VerificationToken;
import com.gibgab.service.database.repository.UserRepository;
import com.gibgab.service.database.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify")
    public String verify(@RequestParam("key") String key){
        VerificationToken verificationToken = verificationTokenRepository.findByVerificationToken(key);

        if(verificationToken == null){
            return "Invalid token";
        }

        ApplicationUser user = userRepository.findById(verificationToken.getUser()).orElse(null);

        if(user == null){
            return "User does not exist";
        }

        user.setVerified(true);

        verificationTokenRepository.delete(verificationToken);
        userRepository.save(user);

        return "User has been verified";

    }
}
