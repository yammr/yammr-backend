package com.gibgab.service.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
import com.gibgab.service.database.entity.Comment;

import java.util.List;


public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByOrderByIdDesc(Pageable page);
    List<Comment> findByIdGreaterThanOrderByIdDesc(int id, Pageable page);
}
