package com.demo.ecommerce.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.view.Rendering;
import com.demo.ecommerce.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: GlobalExceptionHandler
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Mono<Rendering> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return getUserDesc().map(userDesc -> {
            String msg = "BusinessException: " + e.getMessage() + userDesc;
            return Rendering.view("error").modelAttribute("msg", msg).build();
        });
    }

    @ExceptionHandler(AuthenticationException.class)
    public Mono<Rendering> handleException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return getUserDesc().map(userDesc -> {
            String msg = "AuthenticationException: " + e.getMessage() + userDesc;
            return Rendering.view("error").modelAttribute("msg", msg).build();
        });
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Mono<Rendering> handleException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return getUserDesc().map(userDesc -> {
            String msg = "AccessDeniedException: " + e.getMessage() + userDesc;
            return Rendering.view("error").modelAttribute("msg", msg).build();
        });
    }

    @ExceptionHandler(Exception.class)
    public Mono<Rendering> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return getUserDesc().map(userDesc -> {
            String msg = "InternalServerError: " + e.getMessage() + userDesc;
            return Rendering.view("error").modelAttribute("msg", msg).build();
        });
    }

    private Mono<String> getUserDesc() {
        return SecurityUtils.getAuthentication().map(auth -> {
            String username = auth.getName();
            String role = auth.getAuthorities().stream().findFirst()
                    .map(GrantedAuthority::getAuthority).orElse("N/A");
            return String.format(", Current User: %s, Role: %s", username, role);
        }).defaultIfEmpty(", Current User: anonymous");
    }
}
