package com.demo.ecommerce.service;

import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.ecommerce.dao.UserDao;
import com.demo.ecommerce.entity.User;
import com.demo.ecommerce.pojo.UserRegisterDto;

/**
 * 
 * @Description: UserService
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class UserService extends BaseService<UserDao, User, Long> {


    public UserService(UserDao userDao) {
        super(userDao);
    }

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long register(UserRegisterDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        return user.getId();
    }

    public User findUserByNameOrEmail(String name) {
        Specification<User> specification = (root, query, cb) -> cb
                .or(cb.equal(root.get("email"), name), cb.equal(root.get("username"), name));
        Optional<User> optional = super.findOne(specification);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return optional.get();
    }

}
