package com.demo.ecommerce.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: GlobalExceptionHandler
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Mono<Rendering> handleBusinessException(BusinessException ex) {
        return Mono.just(Rendering.view("error").modelAttribute("msg", ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public Mono<Rendering> handleException(Exception ex) {
        return Mono.just(Rendering.view("error")
                .modelAttribute("msg", "Internal Server Error: " + ex.getMessage()).build());
    }
}
