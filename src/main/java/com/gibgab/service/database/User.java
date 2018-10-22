package com.gibgab.service.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.security.crypto.bcrypt.BCrypt;
import lombok.*;

@Entity
public class User {

    public User() {}

    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    private String email;

    private String password_hash;


    public void updatePassword(String password) {
        password_hash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, password_hash);
    }
}
