package com.demo.ecommerce.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @Description: Product Data Transfer Object
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Getter
@Setter
@ToString
public class ProductDto {

    private @Nullable Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Origin must not be blank")
    private String origin;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;

    @DecimalMin(value = "0.1", inclusive = true, message = "Discount must be at least 0.1")
    @DecimalMax(value = "0.9", inclusive = true, message = "Discount must not exceed 0.9")
    private BigDecimal discount;

    @NotNull(message = "Produce date is required")
    private LocalDate produceDate;

    @NotNull(message = "Category Name is required")
    private Long categoryId;

}
