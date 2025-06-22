package com.demo.ecommerce.dao;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 
 * @Description: BaseDao
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@NoRepositoryBean
public interface BaseDao<E, ID> extends JpaRepositoryImplementation<E, ID> {

}
