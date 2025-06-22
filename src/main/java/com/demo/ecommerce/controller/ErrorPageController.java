package com.demo.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import com.demo.ecommerce.utils.SecurityUtils;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: ErrorPageController
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Controller
public class ErrorPageController {

    /**
     * Handle unsuccessful operation
     * 
     * @param exchange
     * @param code
     * @return
     */
    @GetMapping("/error")
    public Mono<Rendering> errorPage(ServerWebExchange exchange,
            @RequestParam(value = "code", required = false, defaultValue = "500") int code) {
        HttpStatusCode status = HttpStatusCode.valueOf(code);
        String message = "Unknown internal server error, please contact the administrator.";
        if (status == HttpStatus.UNAUTHORIZED) {
            message = "Not logged in or session expired, please log in again.";
        } else if (status == HttpStatus.FORBIDDEN) {
            message = "You do not have permission to access this page.";
        }
        StringBuilder str = new StringBuilder(message);
        return getUserDesc().map(userDesc -> {
            str.append(userDesc);
            return Rendering.view("error").modelAttribute("msg", str.toString()).build();
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
