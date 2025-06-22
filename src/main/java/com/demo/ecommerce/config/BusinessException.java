package com.demo.ecommerce.config;

import org.springframework.core.NestedRuntimeException;

/**
 * 
 * @Description: BusinessException
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
public class BusinessException extends NestedRuntimeException {

    private static final long serialVersionUID = -2459293106296996660L;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable e) {
        super(msg, e);
    }
}
