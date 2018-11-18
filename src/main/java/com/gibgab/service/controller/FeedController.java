package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.Post;
import com.gibgab.service.database.repository.PostRepository;
import com.gibgab.service.database.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.List;

@RestController
public class FeedController {

    @Autowired
    private Environment env;

    @Autowired
    private PostRepository postRepository;

    @GetMapping({"/feed"})
    public @ResponseBody List<Post> get_feed() {
        int pageLimit = Integer.parseInt(env.getRequiredProperty("feed.page-limit"));
        return postRepository.findByOrderByIdDesc(PageRequest.of(0, pageLimit));
    }
    
    @GetMapping("/feed/{page}")
    public @ResponseBody List<Post> get_feed(@PathVariable int page) {
        int pageLimit = Integer.parseInt(env.getRequiredProperty("feed.page-limit"));
        return postRepository.findByOrderByIdDesc(PageRequest.of(page, pageLimit));
    }
}
