package com.demo.ecommerce.dao;

import org.springframework.stereotype.Repository;
import com.demo.ecommerce.entity.Product;

/**
 * 
 * @Description: ProductDao
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Repository
public interface ProductDao extends BaseDao<Product, Long> {

}
