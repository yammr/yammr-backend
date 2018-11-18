package com.gibgab.service.database.entity;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCrypt;
import lombok.*;

@Entity
@Table(name="user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {

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

    @Column(name="user_status")
    private byte user_status;
    /*
        Bit flags determine what is the status of the user.
        7654 3210
        0: User is active (banning will set this to 0)
        1: User is registered
        2: User is email-verified
        3: User is a moderator
     */

    @Column(name="fk_campus_id")
    private int campusId = 0;


    public void updatePassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public boolean isActive(){
        return (user_status & 1) != 0;
    }

    public boolean isRegistered(){
        return (user_status & 2) != 0;
    }

    public boolean isVerified(){
        return (user_status & 4) != 0;
    }

    public boolean isModerator() {
        return (user_status & 8) != 0;
    }

    public void setActive(boolean state){
        if(state){
            if(!isActive()) user_status += 1;
        } else {
            if(isActive()) user_status -= 1;
        }
    }

    public void setRegistered(boolean state){
        if(state){
            if(!isRegistered()) user_status += 2;
        } else {
            if(isRegistered()) user_status -= 2;
        }
    }

    public void setVerified(boolean state){
        if(state){
            if(!isVerified()) user_status += 4;
        } else {
            if(isVerified()) user_status -= 4;
        }
    }

    public void setModerator(boolean state){
        if(state){
            if(!isModerator()) user_status += 8;
        } else {
            if(isModerator()) user_status -= 8;
        }
    }

}
