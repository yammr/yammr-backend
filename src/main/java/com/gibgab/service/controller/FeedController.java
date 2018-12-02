package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.Post;
import com.gibgab.service.database.repository.PostRepository;
import com.gibgab.service.database.repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RestController
public class FeedController {

    @Data
    public static class CommentItem {

    }

    @Data
    public static class FeedItem {
        int postId;
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

    @Value("#{'${feed.page-limit}'}")
    private int pageLimit;

    @GetMapping({"/feed"})
    public @ResponseBody List<FeedItem> get_feed_from_index(@RequestParam("start") Optional<Integer> start_id) {
        if ( start_id.isPresent() )
            return transformPosts(postRepository.findByIdGreaterThanOrderByIdDesc(start_id.get(), PageRequest.of(0, pageLimit)));
        else
            return transformPosts(postRepository.findByOrderByIdDesc(PageRequest.of(0, pageLimit)));
    }

    @GetMapping("/feed/{page}")
    public @ResponseBody List<FeedItem> get_feed(@PathVariable int page) {
        return transformPosts(postRepository.findByOrderByIdDesc(PageRequest.of(page, pageLimit)));
    }

    private List<FeedItem> transformPosts(List<Post> posts) {
        List<FeedItem> feed = new ArrayList<FeedItem>(posts.size());
        for ( Post post : posts ) {
            FeedItem item = new FeedItem();
            item.setPostId(post.getId());
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
