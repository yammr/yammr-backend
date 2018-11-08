package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.VerificationToken;
import com.gibgab.service.database.repository.UserRepository;
import com.gibgab.service.database.repository.VerificationTokenRepository;
import com.gibgab.service.security.verification.VerificationConstants;
import com.gibgab.service.security.verification.VerificationEmailSender;
import com.gibgab.service.security.verification.VerificationTokenGenerator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private VerificationEmailSender verificationEmailSender;

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
                ApplicationUser new_user = new ApplicationUser();
                new_user.setEmail(user_info.getEmail());
                new_user.updatePassword(user_info.getPassword());
                new_user.setRegistered(true);
                new_user.setActive(true);

                new_user = userRepository.save(new_user);

                VerificationToken verificationToken = createToken(new_user);
                verificationToken = verificationTokenRepository.save(verificationToken);

                verificationEmailSender.sendVerificationMail(new_user.getEmail(), verificationToken.getVerificationToken());
                return "Created";
            }
        }
        else
            return "Bad";
    }

    private VerificationToken createToken(ApplicationUser new_user) {
        VerificationToken verificationToken = new VerificationToken();
        long now = (new Date()).getTime();
        verificationToken.setExpirationTime(new Timestamp(now + VerificationConstants.tokenExpirationTime));
        verificationToken.setUser(new_user.getId());
        verificationToken.setVerificationToken(VerificationTokenGenerator.generateVerificationToken());
        return verificationToken;
    }
}
