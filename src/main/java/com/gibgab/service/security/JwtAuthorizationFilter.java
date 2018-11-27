package com.gibgab.service.security;

import com.gibgab.service.beans.SecurityConfiguration;
import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.gibgab.service.security.SecurityConstants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, SecurityConfiguration securityConfiguration) {
        super(authenticationManager);
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                if(userRepository == null){
                    ServletContext servletContext = request.getServletContext();
                    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                    userRepository = webApplicationContext.getBean(UserRepository.class);
                }

                ApplicationUser applicationUser = userRepository.findByEmail(user);
                Collection<? extends GrantedAuthority> authorities = getAuthoritiesFromApplicationUser(applicationUser);
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        }

        return null;
    }


    public Collection<? extends GrantedAuthority> getAuthoritiesFromApplicationUser(ApplicationUser user){
        List<String> roles = new ArrayList<>();

        if(!user.isActive()) {
            roles = Collections.emptyList();
        } else {
            if(user.isActive()) roles.add("ROLE_" + securityConfiguration.userRole);
            if(user.isModerator()) roles.add("ROLE_" + securityConfiguration.moderatorRole);
        }

        return getAuthorities(roles);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<String> auths){
        return auths.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
