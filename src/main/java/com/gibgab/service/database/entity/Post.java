package com.gibgab.service.database.entity;

import javax.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name="post")
public class Post {

    public Post() {}

    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="pk_post_id")
    private Integer id;

    @Getter
    @Setter
    @Column(name="text")
    private String text;

    @Getter
    @Setter
    @Column(name="author_name")
    private String authorName;

    @Getter
    @Setter
    @Column(name="latitude")
    private double latitude;

    @Getter
    @Setter
    @Column(name="longitude")
    private double longitude;

    @Getter
    @Setter
    @Column(name="fk_author_id")
    private int authorId = 0;

    @Getter
    @Setter
    @Column(name="fk_campus_id")
    private int campusId = 0;

    @Getter
    @OneToMany
    @JoinColumn(name="fk_post_id")
    private List<Comment> comments;
}
