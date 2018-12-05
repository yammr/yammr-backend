package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.Post;
import com.gibgab.service.database.repository.PostRepository;
import com.gibgab.service.database.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FeedController {

    @Data
    public static class CommentItem {

    }

    @Data
    public static class FeedItem {
        int postId;
        Timestamp postTime;
        String text;
        int score;
        String voteType;
        String authorName;
        String authorPictureUrl;
        String imageUrl;
        int replyCount;
        List<CommentItem> comments;
    }

    @Autowired
    private Environment env;

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Value("#{'${feed.page-limit}'}")
    private int pageLimit;

    @GetMapping({"/feed"})
    public @ResponseBody List<FeedItem> get_feed_from_index(@RequestParam("start") Optional<Integer> start_id) {
        if ( start_id.isPresent() )
            return transformPosts(postRepository.findByIdLessThanOrderByIdDesc(start_id.get(), PageRequest.of(0, pageLimit)));
        else
            return transformPosts(postRepository.findByOrderByIdDesc(PageRequest.of(0, pageLimit)));
    }

    @GetMapping("/feed/{page}")
    public @ResponseBody List<FeedItem> get_feed(@PathVariable int page) {
        return transformPosts(postRepository.findByOrderByIdDesc(PageRequest.of(page, pageLimit)));
    }

    @GetMapping("/feed/me")
    public @ResponseBody List<FeedItem> get_my_posts(Principal principal) {
        ApplicationUser user = userRepository.findByEmail(principal.getName());
        return transformPosts(postRepository.findByAuthorIdOrderByIdDesc(user.getId()));
    }

    private List<FeedItem> transformPosts(List<Post> posts) {
        List<FeedItem> feed = new ArrayList<FeedItem>(posts.size());
        for ( Post post : posts ) {
            FeedItem item = new FeedItem();
            item.setPostId(post.getId());
            item.setPostTime(post.getDate());
            item.setText(post.getText());
            item.setScore(0);
            item.setVoteType(null);
            item.setAuthorName(post.getAuthorName());
            item.setAuthorPictureUrl(null);
            item.setImageUrl(null);
            item.setReplyCount(0);
            item.setComments(new ArrayList<CommentItem>());

            feed.add(item);
        }
        return feed;
    }
}
