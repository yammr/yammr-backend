package com.gibgab.service.database.repository;

import com.gibgab.service.database.entity.PostVote;
import org.springframework.data.repository.CrudRepository;

public interface PostVoteRepository extends CrudRepository<PostVote, Integer> {
    long countByPostId(Integer postId);
    long countByPostIdAndVote(Integer postId, byte vote);
    PostVote findByVoteAuthorAndPostIdAndVote(Integer flagAuthor, Integer postId, byte vote);
}
