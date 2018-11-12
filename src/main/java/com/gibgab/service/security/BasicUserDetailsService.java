package com.gibgab.service.security;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.repository.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class BasicUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public BasicUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findByEmail(email);

        if (applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }

        if(!applicationUser.isActive()){
            throw new DisabledException(email + " is not an active user.");
        }

        if(!applicationUser.isVerified()){
            throw new LockedException(email + " is not verified.");
        }

        return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());
    }
}
