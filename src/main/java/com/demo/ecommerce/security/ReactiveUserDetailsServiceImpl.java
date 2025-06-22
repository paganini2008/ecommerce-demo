package com.demo.ecommerce.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.demo.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: ReactiveUserDetailsServiceImpl
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() -> userService.findUserByNameOrEmail(username))
                .flatMap(user -> {
                    if (user == null) {
                        return Mono.error(new UsernameNotFoundException(
                                "User not found for name: " + username));
                    }
                    return Mono
                            .just(User.withUsername(user.getUsername()).password(user.getPassword())
                                    .authorities("ROLE_AMDIN").disabled(!user.getActive()).build());
                });
    }

}
