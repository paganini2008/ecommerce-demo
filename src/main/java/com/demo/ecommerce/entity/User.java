package com.demo.ecommerce.entity;

import org.hibernate.annotations.DynamicInsert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @Description: User
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@DynamicInsert
@Getter
@Setter
@ToString
@Entity
@Table(name = "example_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 255, unique = true)
    private String username;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "active", nullable = true, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

}

