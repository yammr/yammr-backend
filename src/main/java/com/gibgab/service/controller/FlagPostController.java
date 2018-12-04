package com.gibgab.service.controller;

import com.gibgab.service.beans.AutoModeratorConfiguration;
import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.PostFlag;
import com.gibgab.service.database.repository.PostFlagRepository;
import com.gibgab.service.database.repository.PostRepository;
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
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AutoModeratorConfiguration autoModeratorConfiguration;

    @Data
    @EqualsAndHashCode
    private static class FlagPostParameters {
        Integer postId;
    }

    @RequestMapping("/post/flag")
    public ResponseEntity<String> flagPostById(Principal principal, @RequestBody FlagPostParameters flagInfo){
        String email = principal.getName();
        ApplicationUser flaggingUser = userRepository.findByEmail(email);

        if(postFlagRepository.findByFlagAuthorAndPostId(flaggingUser.getId(), flagInfo.postId) != null) {
            return new ResponseEntity<>("Post already flagged by this user", HttpStatus.BAD_REQUEST);
        }

        PostFlag postFlag = PostFlag.builder().postId(flagInfo.postId).flagAuthor(flaggingUser.getId()).build();
        postFlag.setIsFlag();

        postFlagRepository.save(postFlag);

        if(postFlagRepository.countByPostId(flagInfo.postId) > autoModeratorConfiguration.getMaxFlagsPerPost())
            postRepository.deleteById(flagInfo.postId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
