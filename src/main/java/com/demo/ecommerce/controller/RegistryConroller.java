package com.demo.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import com.demo.ecommerce.pojo.UserRegisterDto;
import com.demo.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 
 * @Description: RegistryConroller
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Slf4j
@RequestMapping("/auth")
@Controller
@RequiredArgsConstructor
public class RegistryConroller {

    private final UserService userService;

    @GetMapping("/register")
    public Mono<Rendering> registerPage() {
        return Mono.just(Rendering.view("register")
                .modelAttribute("registerForm", new UserRegisterDto()).build());
    }

    @PostMapping("/register")
    public Mono<Rendering> handleRegister(
            @Valid @ModelAttribute("registerForm") UserRegisterDto form,
            BindingResult bindingResult, Model model) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword",
                    "Passwords do not match");
        }
        if (bindingResult.hasErrors()) {
            return Mono
                    .just(Rendering.view("register").modelAttribute("registerForm", form).build());
        }
        return Mono.fromCallable(() -> userService.register(form))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(id -> log.info("Register successfully, New user ID generated: " + id))
                .thenReturn(Rendering.redirectTo("/auth/login").build());
    }

}
