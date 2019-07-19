package com.minakov.crudioproject.exc;

public class CustomerNotFoundException extends Throwable {

    private Long customerId;

    public CustomerNotFoundException(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer with id " + customerId + " not found";
    }
}
