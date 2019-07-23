package com.minakov.crudioproject.controller;

import com.minakov.crudioproject.exc.CategoryNotFoundException;
import com.minakov.crudioproject.model.Category;
import com.minakov.crudioproject.repository.impl.CategoryRepositoryImpl;

import java.util.List;

public class CategoryController {

    private CategoryRepositoryImpl repository;

    public CategoryController() {
        this.repository = new CategoryRepositoryImpl();
    }

    public void create(Category category) {
        repository.create(category);
    }

    public void update(Category category) {
        repository.update(category);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public List<Category> list() {
        return repository.findAll();
    }

    public Category findById(Long id) throws CategoryNotFoundException {
        return repository.findById(id);
    }
}
