package com.gibgab.service.controller;

import com.gibgab.service.database.ApplicationUser;
import com.gibgab.service.database.Post;
import com.gibgab.service.database.PostRepository;
import com.gibgab.service.database.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private Environment env;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Data
    private static class PostInfo {
        String text;
    }

    @ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Post not found")
    public class PostNotFoundException extends RuntimeException {
    }

    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    public class BadPostRequestException extends RuntimeException {
    }

    @PostMapping("/post")
    public @ResponseBody Post create_post(@RequestBody PostInfo post_info, Principal principal) {
        if ( post_info.text.equals("") )
            throw new BadPostRequestException();
        ApplicationUser user = userRepository.findByEmail(principal.getName());

        Post post = new Post();
        post.setAuthorId(user.getId());
        post.setText(post_info.text);

        postRepository.save(post);
        return post;
    }

    @GetMapping("/post/{id}")
    public @ResponseBody Post get_post(@PathVariable int post_id) {
        Optional<Post> post = postRepository.findById(post_id);
        if ( post.isPresent() )
            return post.get();

        throw new PostNotFoundException();
    }
}
