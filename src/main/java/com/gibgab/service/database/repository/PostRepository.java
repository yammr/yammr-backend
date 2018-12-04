package com.gibgab.service.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
import com.gibgab.service.database.entity.Post;

import java.util.List;


public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findByOrderByIdDesc(Pageable page);
    List<Post> findByIdLessThanOrderByIdDesc(int id, Pageable page);
}
