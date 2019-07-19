package com.minakov.crudioproject.model;

import com.minakov.crudioproject.model.base.AbstractIdentifiable;

public class Category extends AbstractIdentifiable {

    private String name;

    public Category(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
