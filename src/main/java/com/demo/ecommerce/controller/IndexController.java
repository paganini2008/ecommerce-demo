package com.demo.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: IndexController
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Controller
public class IndexController {

    @GetMapping(path = {"", "/"})
    public Mono<Rendering> indexPage() {
        return Mono.just(Rendering.redirectTo("/auth/login").build());
    }

}
