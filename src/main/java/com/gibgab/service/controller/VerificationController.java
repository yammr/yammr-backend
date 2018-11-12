package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.VerificationToken;
import com.gibgab.service.database.repository.UserRepository;
import com.gibgab.service.database.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificationController {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify")
    public String verify(@RequestParam("key") String key, Model model){
        VerificationToken verificationToken = verificationTokenRepository.findByVerificationToken(key);

        if(verificationToken == null){
            return "verification-error";
        }

        ApplicationUser user = userRepository.findById(verificationToken.getUser()).orElse(null);

        if(user == null){
            return "verification-error";
        }

        user.setVerified(true);

        verificationTokenRepository.delete(verificationToken);
        userRepository.save(user);

        return "email-verified";

    }
}
