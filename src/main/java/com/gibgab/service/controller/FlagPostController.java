package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.PostFlag;
import com.gibgab.service.database.repository.PostFlagRepository;
import com.gibgab.service.database.repository.UserRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class FlagPostController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostFlagRepository postFlagRepository;

    @Data
    @EqualsAndHashCode
    private static class FlagPostParameters {
        Integer postId;
    }

    @RequestMapping("/post/flag")
    public ResponseEntity<String> flagPostById(Principal principal, @RequestBody FlagPostParameters flagInfo){
        String email = principal.getName();
        ApplicationUser flaggingUser = userRepository.findByEmail(email);

        PostFlag postFlag = PostFlag.builder().postId(flagInfo.postId).flag_author(flaggingUser.getId()).build();
        postFlag.setIsFlag();

        postFlagRepository.save(postFlag);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
