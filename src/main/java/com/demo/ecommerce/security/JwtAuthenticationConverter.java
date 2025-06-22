package com.demo.ecommerce.security;

import java.util.Optional;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: JwtAuthenticationConverter
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    private final ReactiveUserDetailsService userDetailsService;

    public JwtAuthenticationConverter(ReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token =
                Optional.ofNullable(exchange.getRequest().getCookies().getFirst("Authorization"))
                        .map(HttpCookie::getValue).orElse(null);

        if (token == null || !JwtUtils.validateToken(token)) {
            return Mono.empty();
        }

        String username = JwtUtils.extractUsername(token);
        return userDetailsService.findByUsername(username).map(user -> {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                    user.getAuthorities());
        });
    }
}
