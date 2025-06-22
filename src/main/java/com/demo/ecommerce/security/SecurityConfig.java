package com.demo.ecommerce.security;

import java.net.URI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @Description: SecurityConfig
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SecurityConfig {

    private final ReactiveUserDetailsService userDetailsService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter jwtAuthFilter =
                new AuthenticationWebFilter(authenticationManager());
        jwtAuthFilter.setServerAuthenticationConverter(
                new JwtAuthenticationConverter(userDetailsService));
        jwtAuthFilter.setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers("/product/**"));
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .exceptionHandling(ex -> ex.authenticationEntryPoint((exchange, exx) -> {
                    exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
                    exchange.getResponse().getHeaders().setLocation(URI.create("/error?code=401"));
                    return exchange.getResponse().setComplete();
                }).accessDeniedHandler((exchange, exx) -> {
                    exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
                    exchange.getResponse().getHeaders().setLocation(URI.create("/error?code=403"));
                    return exchange.getResponse().setComplete();
                }))
                .authorizeExchange(
                        exchange -> exchange
                                .pathMatchers("/", "/auth/**", "/error", "/v3/api-docs/**",
                                        "/swagger-ui.html")
                                .permitAll().anyExchange().authenticated())
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION).build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        JwtReactiveAuthenticationManager manager =
                new JwtReactiveAuthenticationManager(userDetailsService, passwordEncoder());
        return manager;
    }

    @ConditionalOnMissingBean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
