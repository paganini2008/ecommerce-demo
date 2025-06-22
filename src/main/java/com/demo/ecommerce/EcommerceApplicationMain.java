package com.demo.ecommerce;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @Description: EcommerceApplicationMain
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@SpringBootApplication
public class EcommerceApplicationMain {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplicationMain.class, args);
    }

}
