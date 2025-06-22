package com.demo.ecommerce.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: JwtReactiveAuthenticationManager
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public JwtReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        if (authentication.isAuthenticated()) {
            return userDetailsService.findByUsername(username)
                    .map(userDetails -> createUsernamePasswordAuthenticationToken(userDetails));
        }
        String rawPassword = authentication.getCredentials().toString();
        return userDetailsService.findByUsername(username).filter(
                userDetails -> passwordEncoder.matches(rawPassword, userDetails.getPassword()))
                .switchIfEmpty(
                        Mono.error(new BadCredentialsException("Invalid username or password")))
                .map(userDetails -> createUsernamePasswordAuthenticationToken(userDetails));
    }

    protected Authentication createUsernamePasswordAuthenticationToken(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
                        userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return authenticationToken;
    }
}
