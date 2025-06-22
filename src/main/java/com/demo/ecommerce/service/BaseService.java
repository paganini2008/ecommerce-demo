package com.demo.ecommerce.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.demo.ecommerce.dao.BaseDao;

/**
 * 
 * @Description: BaseService
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
public abstract class BaseService<DAO extends BaseDao<E, ID>, E, ID> {

    protected BaseService(DAO dao) {
        this.dao = dao;
    }

    protected final DAO dao;

    public E save(E entity) {
        return dao.save(entity);
    }

    public List<E> save(Iterable<E> entities) {
        return dao.saveAll(entities);
    }

    public Optional<E> getById(ID id) {
        return dao.findById(id);
    }

    public void delete(E entity) {
        dao.delete(entity);
    }

    public void deleteById(ID id) {
        Optional<E> opt = getById(id);
        if (opt.isPresent()) {
            delete(opt.get());
        }
    }

    public List<E> findAll() {
        return dao.findAll();
    }

    public List<E> findAll(Sort sort) {
        return dao.findAll(sort);
    }

    public Optional<E> findOne(Example<E> example) {
        return dao.findOne(example);
    }

    public Optional<E> findOne(Specification<E> specification) {
        return dao.findOne(specification);
    }

    public Page<E> findByPage(Pageable pageable) {
        return dao.findAll(pageable);
    }

}
