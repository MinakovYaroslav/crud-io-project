package com.minakov.crudioproject.exc;

public class CategoryNotFoundException extends Throwable {

    private Long categoryId;

    public CategoryNotFoundException(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Category with id " + categoryId + " not found";
    }
}
