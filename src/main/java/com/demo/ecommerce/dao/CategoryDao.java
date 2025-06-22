package com.demo.ecommerce.dao;

import org.springframework.stereotype.Repository;
import com.demo.ecommerce.entity.Category;

/**
 * 
 * @Description: CategoryDao
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Repository
public interface CategoryDao extends BaseDao<Category, Long> {

}
