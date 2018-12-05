package com.gibgab.service.database.repository;

import com.gibgab.service.database.entity.PostFlag;
import org.springframework.data.repository.CrudRepository;

public interface PostFlagRepository  extends CrudRepository<PostFlag, Integer> {
    long countByPostId(Integer postId);
    PostFlag findByFlagAuthorAndPostIdAndVote(Integer flagAuthor, Integer postId, byte vote);
}
