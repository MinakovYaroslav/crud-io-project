package com.minakov.crudioproject.model.base;

public class AbstractIdentifiable {

    public AbstractIdentifiable(Long id) {
        this.id = id;
    }

    public AbstractIdentifiable() {
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "id: " + id;
    }
}
