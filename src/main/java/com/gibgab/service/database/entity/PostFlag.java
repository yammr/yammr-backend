package com.gibgab.service.database.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_vote")
public class PostFlag {

    @Id
    @Getter
    @Setter
    @Column(name = "pk_post_vote_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "upvote")
    private byte vote;

    @Getter
    @Setter
    @Column(name = "fk_author_id")
    private Integer flagAuthor;

    @Getter
    @Setter
    @Column(name = "fk_post_id")
    private Integer postId;

    public void setIsFlag(){
        vote = 0x3;
    }

    public boolean getIsFlag(){
        return (vote & 0x3) == 0x3;
    }
}
