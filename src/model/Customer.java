package model;

import model.base.AbstractIdentifiable;

public class Customer extends AbstractIdentifiable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer(Long id, String name) {
        super(id);
        this.name = name;
    }
}
