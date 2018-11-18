package com.gibgab.service.database.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BanEvent {


    @Id
    @Column(name="pk_ban_event_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter
    private Integer id;

    @Column(name="start_date")
    @Getter
    @Setter
    private Timestamp startTime;

    @Column(name="end_date")
    @Getter
    @Setter
    private Timestamp endTime;

    @Column(name="fk_banner_id")
    @Getter
    @Setter
    private Integer bannerId;

    @Column(name="fk_banned_id")
    @Getter
    @Setter
    private Integer bannedId;


}
