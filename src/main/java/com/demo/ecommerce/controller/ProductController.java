package com.demo.ecommerce.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import com.demo.ecommerce.entity.Product;
import com.demo.ecommerce.pojo.ProductDto;
import com.demo.ecommerce.pojo.ProductVo;
import com.demo.ecommerce.service.CategoryService;
import com.demo.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 
 * @Description: ProductController
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public Mono<Rendering> productListPage(Model model) {
        return Mono.fromCallable(() -> {
            model.addAttribute("products",
                    productService.findAll(Sort.by(Sort.Order.desc("updateDate"))));
            return Rendering.view("product/list").build();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/create")
    public Mono<Rendering> createProductPage(Model model) {
        return Mono.fromCallable(() -> {
            model.addAttribute("productForm", new ProductDto());
            model.addAttribute("categories", categoryService.findAll());
            return Rendering.view("product/create").build();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create Product
     * 
     * @param form
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/create")
    public Mono<Rendering> createProduct(@Valid @ModelAttribute("productForm") ProductDto form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return Mono
                    .fromCallable(() -> Rendering.view("product/create")
                            .modelAttribute("productForm", form)
                            .modelAttribute("categories", categoryService.findAll()).build())
                    .subscribeOn(Schedulers.boundedElastic());
        }
        return Mono.fromCallable(() -> productService.save(form))
                .thenReturn(Rendering.redirectTo("/product/list").build());
    }

    @GetMapping("/edit/{id}")
    public Mono<Rendering> editProductPage(@PathVariable("id") Long id, Model model) {
        return Mono.fromCallable(() -> {
            Product product = productService.getById(id).get();
            ProductVo vo = new ProductVo();
            BeanUtils.copyProperties(product, vo);
            vo.setCategoryId(product.getCategory().getId());
            return vo;
        }).map(vo -> Rendering.view("product/edit").modelAttribute("productForm", vo)
                .modelAttribute("categories", categoryService.findAll()).build());
    }

    /**
     * Update Product
     * 
     * @param id
     * @param form
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/edit/{id}")
    public Mono<Rendering> editProduct(@PathVariable("id") Long id,
            @Valid @ModelAttribute("productForm") ProductDto form, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return Mono
                    .fromCallable(() -> Rendering.view("product/edit")
                            .modelAttribute("productForm", form)
                            .modelAttribute("categories", categoryService.findAll()).build())
                    .subscribeOn(Schedulers.boundedElastic());
        }
        return Mono.fromCallable(() -> productService.save(form))
                .thenReturn(Rendering.redirectTo("/product/list").build());
    }

    /**
     * View product detail by given id
     * 
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/view/{id}")
    public Mono<Rendering> viewProduct(@PathVariable("id") Long id, Model model) {
        return Mono.fromCallable(() -> {
            model.addAttribute("product", productService.getById(id).get());
            return Rendering.view("product/detail").build();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Delete product by given id
     * 
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public Mono<Rendering> deleteProduct(@PathVariable("id") Long id) {
        return Mono.fromRunnable(() -> productService.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .thenReturn(Rendering.redirectTo("/product/list").build());
    }
}
