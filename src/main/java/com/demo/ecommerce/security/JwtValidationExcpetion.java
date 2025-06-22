package com.demo.ecommerce.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @Description: JwtValidationExcpetion
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
public class JwtValidationExcpetion extends AuthenticationException {

    private static final long serialVersionUID = -6159583590442310750L;

    public JwtValidationExcpetion(String msg, Throwable cause) {
        super(msg, cause);
    }


    public JwtValidationExcpetion(String msg) {
        super(msg);
    }

}
