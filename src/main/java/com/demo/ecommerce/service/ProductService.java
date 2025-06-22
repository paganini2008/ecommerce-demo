package com.demo.ecommerce.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.ecommerce.config.BusinessException;
import com.demo.ecommerce.dao.ProductDao;
import com.demo.ecommerce.entity.Category;
import com.demo.ecommerce.entity.Product;
import com.demo.ecommerce.pojo.ProductDto;

/**
 * 
 * @Description: ProductService
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class ProductService extends BaseService<ProductDao, Product, Long> {

    public ProductService(ProductDao dao) {
        super(dao);
    }

    public Product save(ProductDto productDto) {
        Product product;
        if (productDto.getId() != null) {
            product = getById(productDto.getId()).get();
            product.setUpdateDate(LocalDateTime.now());
        } else {
            Specification<Product> specification =
                    (root, query, cb) -> cb.equal(root.get("name"), productDto.getName());
            Optional<Product> opt = super.findOne(specification);
            if (opt.isPresent()) {
                throw new BusinessException("Duplicated product name: " + productDto.getName());
            }
            product = new Product();
        }
        BeanUtils.copyProperties(productDto, product);
        product.setCategory(new Category(productDto.getCategoryId()));
        return super.save(product);
    }

}
