package com.gibgab.service.database;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCrypt;
import lombok.*;

@Entity
@Table(name="User")
public class ApplicationUser {

    public ApplicationUser() {}

    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @Column(name="email")
    private String email;

    @Getter
    @Column(name="password_hash")
    private String password;


    public void updatePassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }
}
