package com.demo.ecommerce.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @Description: JpaConfig
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@EntityScan(basePackages = {"com.demo.ecommerce.entity"})
@EnableJpaRepositories(basePackages = {"com.demo.ecommerce.dao"})
@Configuration(proxyBeanMethods = false)
public class JpaConfig {

}
