package com.jovana.nsibackend.controller;

import com.jovana.nsibackend.repository.CategoryRepository;
import com.jovana.nsibackend.resource.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by jovana on 14.11.2018
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public Set<CategoryResponse> getCategories() {
        return categoryRepository.findAllCategories();
    }

}
