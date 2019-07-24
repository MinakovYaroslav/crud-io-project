package com.minakov.crudioproject.repository.impl;

import com.minakov.crudioproject.exc.CustomerNotFoundException;
import com.minakov.crudioproject.io.IOUtil;
import com.minakov.crudioproject.model.Customer;
import com.minakov.crudioproject.model.base.AbstractIdentifiable;
import com.minakov.crudioproject.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {

    private static final String FILE_PATH = "C:\\projects\\crud-io-project\\src\\main\\resources\\Customers.txt";

    private List<Customer> customers;

    public CustomerRepositoryImpl() {
        this.customers = customers();
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public void delete(Long id) {
        try {
            Customer customer = findById(id);
            customers.remove(customer);
            List<String[]> data = objToArray(customers);
            IOUtil.write(data, FILE_PATH);
        } catch (CustomerNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Customer findById(Long id) throws CustomerNotFoundException {
        return customers.stream()
                .filter(c -> c.getId().equals(id)).findAny().orElseThrow(() -> new CustomerNotFoundException("Customer with id: " + id + " not found"));
    }

    @Override
    public Customer create(Customer customer) {
        long lastId = customers.stream().mapToLong(AbstractIdentifiable::getId).max().orElse(0);
        customer.setId(++lastId);
        customers.add(customer);
        List<String[]> data = objToArray(customers);
        IOUtil.write(data, FILE_PATH);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        try {
            Customer old = findById(customer.getId());
            int index = customers.indexOf(old);
            customers.set(index, customer);
            List<String[]> data = objToArray(customers);
            IOUtil.write(data, FILE_PATH);
        } catch (CustomerNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return customer;
    }

    private List<Customer> customers() {
        List<String[]> data = IOUtil.read(FILE_PATH);
        List<Customer> customers = new ArrayList<>();
        for (String[] fields : data) {
            Long id = Long.valueOf(fields[0]);
            String name = fields[1];
            Customer customer = new Customer(id, name);
            customers.add(customer);
        }
        return customers;
    }

    private List<String[]> objToArray(List<Customer> customers) {
        List<String[]> data = new ArrayList<>();
        for (Customer customer : customers) {
            String id = String.valueOf(customer.getId());
            String name = customer.getName();
            data.add(new String[]{id, name});
        }
        return data;
    }
}
