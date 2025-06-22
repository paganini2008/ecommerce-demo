package com.demo.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.DynamicInsert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @Description: Product
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@DynamicInsert
@Getter
@Setter
@ToString
@Entity
@Table(name = "example_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false, precision = 11, scale = 1)
    private BigDecimal price;

    @Column(name = "discount", nullable = true, precision = 4, scale = 1)
    private BigDecimal discount;

    @Column(name = "produce_date", nullable = false)
    private LocalDate produceDate;

    @Column(name = "origin", nullable = true, length = 45)
    private String origin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "create_date", nullable = true,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = true,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateDate;


}
