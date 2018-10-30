package com.gibgab.service.database;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCrypt;
import lombok.*;

@Entity
@Table(name="user")
public class ApplicationUser {

    public ApplicationUser() {}

    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="pk_user_id")
    private Integer id;

    @Getter
    @Setter
    @Column(name="email")
    private String email;

    @Getter
    @Column(name="password_hash")
    private String password;

    @Column(name="username")
    private String username;

    @Column(name="is_moderator")
    private byte moderator;

    @Column(name="fk_campus_id")
    private int campusId = 0;


    public void updatePassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }
}
