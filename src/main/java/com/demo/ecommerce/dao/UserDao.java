package com.demo.ecommerce.dao;

import org.springframework.stereotype.Repository;
import com.demo.ecommerce.entity.User;

/**
 * 
 * @Description: UserDao
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Repository
public interface UserDao extends BaseDao<User, Long> {

}
