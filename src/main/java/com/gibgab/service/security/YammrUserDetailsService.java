package com.gibgab.service.security;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.BanEvent;
import com.gibgab.service.database.repository.BanEventRepository;
import com.gibgab.service.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class YammrUserDetailsService implements UserDetailsService {

    @Autowired
    private String userRole;
    @Autowired
    private String moderatorRole;

    private UserRepository userRepository;

    @Autowired
    private BanEventRepository banEventRepository;

    public YammrUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        ApplicationUser applicationUser = userRepository.findByEmail(email);

        if (applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }

        if(!applicationUser.isActive()){
            if(checkIsNoLongerBanned(applicationUser)){
                applicationUser.setActive(true);
                applicationUser = userRepository.save(applicationUser);
            } else {
                throw new DisabledException(email + " is not an active user.");
            }
        }

        if(!applicationUser.isVerified()){
            throw new LockedException(email + " is not verified.");
        }

        List<String> privileges = new ArrayList<>();

        if(applicationUser.isActive()) {
            if(applicationUser.isVerified()){
                privileges.add("ROLE_" + userRole);
            }
            if (applicationUser.isModerator()) {
                privileges.add("ROLE_" + moderatorRole);
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String priv : privileges) {
                (authorities).add(new SimpleGrantedAuthority(priv));
            }

            return new User(applicationUser.getEmail(), applicationUser.getPassword(), authorities);
        } else {
            return new User(applicationUser.getEmail(), applicationUser.getPassword(), true, true, true, false, emptyList());
        }
    }

    private boolean checkIsNoLongerBanned(ApplicationUser applicationUser) {
        Timestamp now = new Timestamp((new Date()).getTime());
        BanEvent banEvent = banEventRepository.findByBannedIdAndEndTimeAfter(applicationUser.getId(), now);

        if(banEvent != null){
            throw new DisabledException(applicationUser.getEmail() + " is banned until " + banEvent.getEndTime());
        } else {
            return true;
        }
    }
}
