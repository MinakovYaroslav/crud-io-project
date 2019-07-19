package com.minakov.crudioproject.repository.impl;

import com.minakov.crudioproject.exc.CategoryNotFoundException;
import com.minakov.crudioproject.io.IOUtil;
import com.minakov.crudioproject.model.Category;
import com.minakov.crudioproject.model.base.AbstractIdentifiable;
import com.minakov.crudioproject.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String FILE_PATH = "";

    @Override
    public List<Category> findAll() {
        return categories();
    }

    @Override
    public void delete(Long id) throws CategoryNotFoundException {
        Category category = findById(id);
        List<Category> categories = categories();
        categories.remove(category);
        List<String[]> data = objToArray(categories);
        IOUtil.write(data, FILE_PATH);
    }

    @Override
    public Category findById(Long id) throws CategoryNotFoundException {
        return categories().stream()
                .filter(c -> c.getId().equals(id)).findAny().orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category save(Category category) throws CategoryNotFoundException {
        List<Category> categories = categories();
        if (category.isNew()) {
            if (categories.size() != 0) {
                categories.sort(Comparator.comparing(AbstractIdentifiable::getId));
                Long lastId = categories.get(categories.size() - 1).getId();
                category.setId(lastId + 1);
            } else category.setId(1L);
            categories.add(category);
        } else {
            Category old = findById(category.getId());
            int index = categories.indexOf(old);
            categories.set(index, category);
        }
        List<String[]> data = objToArray(categories);
        IOUtil.write(data, FILE_PATH);

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
