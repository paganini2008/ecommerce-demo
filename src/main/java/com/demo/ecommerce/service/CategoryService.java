package com.demo.ecommerce.service;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import com.demo.ecommerce.dao.CategoryDao;
import com.demo.ecommerce.entity.Category;
import jakarta.annotation.PostConstruct;

/**
 * 
 * @Description: CategoryService
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Service
public class CategoryService extends BaseService<CategoryDao, Category, Long> {

    public CategoryService(CategoryDao dao) {
        super(dao);
    }

    @PostConstruct
    public void init() {
        List<Category> categories = findAll();
        if (CollectionUtils.isEmpty(categories)) {
            String[] defaultCats = {"Category1", "Category2", "Category3"};
            for (String cat : defaultCats) {
                Category category = new Category(cat);
                save(category);
            }
        }
    }

}
