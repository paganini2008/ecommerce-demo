package com.demo.ecommerce.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @Description: Product Value Object
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Getter
@Setter
@ToString
public class ProductVo {

    private Long id;

    private String name;

    private BigDecimal price;

    private BigDecimal discount;

    private LocalDate produceDate;

    private String origin;

    private Long categoryId;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
