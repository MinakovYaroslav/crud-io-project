package com.minakov.crudioproject.controller;

import com.minakov.crudioproject.exc.CustomerNotFoundException;
import com.minakov.crudioproject.model.Customer;
import com.minakov.crudioproject.repository.impl.CustomerRepositoryImpl;

import java.util.List;

public class CustomerController {

    private CustomerRepositoryImpl repository;

    public CustomerController() {
        this.repository = new CustomerRepositoryImpl();
    }

    public void create(Customer customer) {
        repository.create(customer);
    }

    public void update(Customer customer) {
        repository.update(customer);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public List<Customer> list() {
        return repository.findAll();
    }

    public Customer findById(Long id) throws CustomerNotFoundException {
        return repository.findById(id);
    }
}
