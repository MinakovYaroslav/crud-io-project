package com.minakov.crudioproject.repository.impl;

import com.minakov.crudioproject.exc.CategoryNotFoundException;
import com.minakov.crudioproject.io.IOUtil;
import com.minakov.crudioproject.model.Category;
import com.minakov.crudioproject.model.base.AbstractIdentifiable;
import com.minakov.crudioproject.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String FILE_PATH = "C:\\projects\\crud-io-project\\src\\main\\resources\\Category.txt";

    private List<Category> categories;

    public CategoryRepositoryImpl() {
        this.categories = categories();
    }

    @Override
    public List<Category> findAll() {
        return categories;
    }

    @Override
    public void delete(Long id) {
        try {
            Category category = findById(id);
            categories.remove(category);
            List<String[]> data = objToArray(categories);
            IOUtil.write(data, FILE_PATH);
        } catch (CategoryNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Category findById(Long id) throws CategoryNotFoundException {
        return categories.stream()
                .filter(c -> c.getId().equals(id)).findAny().orElseThrow(() -> new CategoryNotFoundException("Category with id: " + id + " not found"));
    }

    @Override
    public Category create(Category category) {
        long lastId = categories.stream().mapToLong(AbstractIdentifiable::getId).max().orElse(0);
        category.setId(++lastId);
        categories.add(category);
        List<String[]> data = objToArray(categories);
        IOUtil.write(data, FILE_PATH);
        return category;
    }

    @Override
    public Category update(Category category) {
        try {
            Category old = findById(category.getId());
            int index = categories.indexOf(old);
            categories.set(index, category);
            List<String[]> data = objToArray(categories);
            IOUtil.write(data, FILE_PATH);
        } catch (CategoryNotFoundException e) {
            System.err.println(e.toString());
        }
        return category;
    }

    private List<Category> categories() {
        List<String[]> data = IOUtil.read(FILE_PATH);
        List<Category> categories = new ArrayList<>();
        for (String[] fields : data) {
            Long id = Long.valueOf(fields[0]);
            String name = fields[1];
            Category category = new Category(id, name);
            categories.add(category);
        }
        return categories;
    }

    private List<String[]> objToArray(List<Category> categories) {
        List<String[]> data = new ArrayList<>();
        for (Category category : categories) {
            String id = String.valueOf(category.getId());
            String name = category.getName();
            data.add(new String[]{id, name});
        }
        return data;
    }
}
