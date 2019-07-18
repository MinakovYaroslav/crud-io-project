package repository.impl;

import io.IOUtil;
import model.Category;
import repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String FILE_PATH = "";

    @Override
    public List<Category> findAll() throws Throwable {
        return null;
    }

    @Override
    public void delete(Long aLong) throws Throwable {

    }

    @Override
    public Category findById(Long aLong) throws Throwable {
        return null;
    }

    @Override
    public Category save(Category category) {
        return null;
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
            data.add(new String[] {id, name});
        }
        return data;
    }
}
