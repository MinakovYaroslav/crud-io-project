package com.minakov.crudioproject.model;

import com.minakov.crudioproject.model.base.AbstractIdentifiable;

import java.util.Set;

public class Project extends AbstractIdentifiable {

    private String name;

    private ProjectStatus status;

    private Set<Category> categories;

    private Customer customer;

    public Project(Long id, String name, ProjectStatus status, Set<Category> categories, Customer customer) {
        super(id);
        this.name = name;
        this.status = status;
        this.categories = categories;
        this.customer = customer;
    }

    public Project(String name, ProjectStatus status, Set<Category> categories, Customer customer) {
        this.name = name;
        this.status = status;
        this.categories = categories;
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return super.toString() + ", name: " + name + ", categories: " + categories + ", customer: [" + customer.toString() + "]";
    }
}
