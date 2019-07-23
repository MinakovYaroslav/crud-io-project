package com.minakov.crudioproject.exc;

public class ProjectNotFoundException extends Throwable {

    private String message;

    public ProjectNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
