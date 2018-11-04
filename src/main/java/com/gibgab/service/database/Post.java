package com.gibgab.service.database;

import javax.persistence.*;

import lombok.*;

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
    @Column(name="author_name")
    private String author_name;

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
}
