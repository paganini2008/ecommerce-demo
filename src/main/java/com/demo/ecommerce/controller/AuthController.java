package com.demo.ecommerce.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import com.demo.ecommerce.pojo.UserLoginDto;
import com.demo.ecommerce.security.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: AuthController
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ReactiveAuthenticationManager authenticationManager;


    @GetMapping("/login")
    public Mono<Rendering> loginPage(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getCookies().getFirst("Authorization"))
                .map(cookie -> cookie.getValue()).filter(token -> JwtUtils.validateToken(token))
                .flatMap(token -> Mono.just(Rendering.redirectTo("/product/list").build()))
                .switchIfEmpty(Mono.just(Rendering.view("login")
                        .modelAttribute("loginForm", new UserLoginDto()).build()));
    }

    /**
     * Do login with username and password
     * 
     * @param loginForm
     * @param bindingResult
     * @param exchange
     * @return
     */
    @PostMapping("/login")
    public Mono<Rendering> login(@Valid @ModelAttribute("loginForm") UserLoginDto loginForm,
            BindingResult bindingResult, ServerWebExchange exchange) {
        if (bindingResult.hasErrors()) {
            return Mono
                    .just(Rendering.view("login").modelAttribute("loginForm", loginForm).build());
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(),
                        loginForm.getPassword());
        authenticationToken.setAuthenticated(false);
        return authenticationManager.authenticate(authenticationToken)
                .map(auth -> JwtUtils.generateToken(auth.getName())).doOnNext(token -> {
                    log.info("Generate Token: " + token);
                    ResponseCookie cookie = ResponseCookie.from("Authorization", token).path("/")
                            .httpOnly(false).maxAge(7 * 24 * 60 * 60).build();
                    exchange.getResponse().addCookie(cookie);
                }).thenReturn(Rendering.redirectTo("/product/list").build());
    }

    /**
     * Do logout
     * 
     * @param exchange
     * @return
     */
    @GetMapping("/logout")
    public Mono<Rendering> logout(ServerWebExchange exchange) {
        ResponseCookie clearCookie =
                ResponseCookie.from("Authorization", "").path("/").maxAge(0).build();
        exchange.getResponse().addCookie(clearCookie);
        return Mono.just(
                Rendering.view("login").modelAttribute("loginForm", new UserLoginDto()).build());
    }
}
