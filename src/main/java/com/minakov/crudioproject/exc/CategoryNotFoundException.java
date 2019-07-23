package com.minakov.crudioproject.exc;

public class CategoryNotFoundException extends Throwable {

    private String message;

    public CategoryNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }


}
