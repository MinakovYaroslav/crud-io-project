package com.minakov.crudioproject.controller;

import com.minakov.crudioproject.exc.ProjectNotFoundException;
import com.minakov.crudioproject.model.Project;
import com.minakov.crudioproject.repository.impl.ProjectRepositoryImpl;

import java.util.List;

public class ProjectController {

    private ProjectRepositoryImpl repository;

    public ProjectController() {
        this.repository = new ProjectRepositoryImpl();
    }

    public void create(Project project) {
        repository.create(project);
    }

    public void update(Project project) {
        repository.update(project);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public List<Project> list() {
        return repository.findAll();
    }

    public Project findById(Long id) throws ProjectNotFoundException {
        return repository.findById(id);
    }
}
