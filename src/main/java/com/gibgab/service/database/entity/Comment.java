package com.gibgab.service.database.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name="post")
public class Comment {

    public Comment() {}

    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="pk_comment_id")
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
    @Column(name="fk_post_id")
    private int postId = 0;

    @Getter
    @Setter
    @Column(name="score")
    private int score = 0;
}
