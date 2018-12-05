package com.gibgab.service.database.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_vote")
public class PostVote {

    public static final byte UP_VOTE = (byte)0x1;
    public static final byte DOWN_VOTE = (byte)0x0;

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
    private Integer voteAuthor;

    @Getter
    @Setter
    @Column(name = "fk_post_id")
    private Integer postId;

    public void setIsUpVote(){
        vote = 0x1;
    }

    public void setIsDownVote(){
        vote = 0x0;
    }

    public boolean getIsUpVote(){
        return (vote & 0x1) == 0x1;
    }

    public boolean getIsDownVote(){
        return (vote) == 0x0;
    }
}
