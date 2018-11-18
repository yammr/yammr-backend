package com.gibgab.service.database.repository;

import org.springframework.data.repository.CrudRepository;
import com.gibgab.service.database.entity.Post;


public interface PostRepository extends CrudRepository<Post, Integer> {
}
