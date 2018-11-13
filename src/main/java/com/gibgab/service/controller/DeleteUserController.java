package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DeleteUserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/delete")
    public ResponseEntity<String> deleteUser(Principal principal){
        String email = principal.getName();

        ApplicationUser user = userRepository.findByEmail(email);

        if(user == null) return new ResponseEntity<>(email, HttpStatus.BAD_REQUEST);

        userRepository.delete(user);

        return new ResponseEntity<>("Deleted", HttpStatus.OK );
    }
}
