package com.demo.ecommerce.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

/**
 * 
 * @Description: SecurityUtils
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@UtilityClass
public class SecurityUtils {

    public Mono<Authentication> getAuthentication() {
        return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
            return securityContext.getAuthentication();
        });
    }
}
