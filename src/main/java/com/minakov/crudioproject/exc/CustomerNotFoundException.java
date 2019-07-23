package com.minakov.crudioproject.exc;

public class CustomerNotFoundException extends Throwable {

    private String message;

    public CustomerNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
