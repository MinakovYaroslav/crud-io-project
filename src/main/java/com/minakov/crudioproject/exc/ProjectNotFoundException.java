package com.minakov.crudioproject.exc;

public class ProjectNotFoundException extends Throwable {

    private Long projectId;

    public ProjectNotFoundException(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Project with id " + projectId + " not found";
    }
}
