package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.Comment;
import com.gibgab.service.database.repository.CommentRepository;
import com.gibgab.service.database.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.Optional;

@RestController
public class CommentController {

    @Autowired
    private Environment env;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Data
    private static class CommentInfo {
        int post_id;
        String text;
    }

    @ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Comment not found")
    public class CommentNotFoundException extends RuntimeException {
    }

    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    public class BadCommentRequestException extends RuntimeException {
    }

    @PostMapping("/comment")
    public @ResponseBody Comment create_comment(@RequestBody CommentInfo comment_info, Principal principal) {
        if ( comment_info.text != null && comment_info.text.equals("") )
            throw new BadCommentRequestException();
        ApplicationUser user = userRepository.findByEmail(principal.getName());

        Comment comment = new Comment();
        comment.setAuthorId(user.getId());
        comment.setAuthorName(user.getUsername());
        comment.setText(comment_info.text);
        comment.setPostId(comment_info.post_id);

        commentRepository.save(comment);
        return comment;
    }

    @GetMapping("/comment/{comment_id}")
    public @ResponseBody Comment get_comment(@PathVariable int comment_id) {
        Optional<Comment> comment = commentRepository.findById(comment_id);
        if ( comment.isPresent() )
            return comment.get();

        throw new CommentNotFoundException();
    }
}
