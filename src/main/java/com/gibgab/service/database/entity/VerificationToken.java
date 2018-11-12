package com.gibgab.service.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="verification")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="pk_verification_id")
    @Getter
    private Integer id;

    @Column(name="token")
    @Setter
    @Getter
    private String verificationToken;

    @Column(name="expiration_date")
    @Setter
    @Getter
    private Timestamp expirationTime;

    @Column(name="fk_user_id")
    @Setter
    @Getter
    private Integer user;

}
