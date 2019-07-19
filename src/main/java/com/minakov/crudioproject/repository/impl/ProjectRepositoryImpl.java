package com.minakov.crudioproject.repository.impl;

import com.minakov.crudioproject.exc.CategoryNotFoundException;
import com.minakov.crudioproject.exc.CustomerNotFoundException;
import com.minakov.crudioproject.exc.ProjectNotFoundException;
import com.minakov.crudioproject.io.IOUtil;
import com.minakov.crudioproject.model.Category;
import com.minakov.crudioproject.model.Customer;
import com.minakov.crudioproject.model.Project;
import com.minakov.crudioproject.model.ProjectStatus;
import com.minakov.crudioproject.model.base.AbstractIdentifiable;
import com.minakov.crudioproject.repository.ProjectRepository;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectRepositoryImpl implements ProjectRepository {

    private static final String FILE_PATH = "";

    private List<Project> projects;

    public ProjectRepositoryImpl() {
        this.projects = projects();
    }

    @Override
    public List<Project> findAll() {
        return projects();
    }

    @Override
    public void delete(Long id) throws ProjectNotFoundException {
        Project project = findById(id);
        projects.remove(project);
        List<String[]> data = objToArray(projects);
        IOUtil.write(data, FILE_PATH);
    }

    @Override
    public Project findById(Long id) throws ProjectNotFoundException {
        return projects.stream()
                .filter(p -> p.getId().equals(id)).findAny().orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Override
    public Project save(Project project) throws ProjectNotFoundException {
        if (project.isNew()) {
            long lastId = projects.stream().mapToLong(AbstractIdentifiable::getId).max().getAsLong();
            project.setId(lastId + 1);
            projects.add(project);
        } else {
            Project old = findById(project.getId());
            int index = projects.indexOf(old);
            projects.set(index, project);
        }
        return project;
    }

    private List<Project> projects() {
        List<String[]> data = IOUtil.read(FILE_PATH);
        CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();
        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
        List<Project> projects = new ArrayList<>();
        for (String[] fields : data) {
            try {
                Long id = Long.valueOf(fields[0]);
                String name = fields[1];
                ProjectStatus status = ProjectStatus.valueOf(fields[2]);
                String[] categoriesArray = fields[3].split(",");
                Set<Category> categories = new HashSet<>();
                for (String c : categoriesArray) {
                    categories.add(categoryRepository.findById(Long.valueOf(c)));
                }
                Customer customer = customerRepository.findById(Long.valueOf(fields[4]));
                projects.add(new Project(id, name, status, categories, customer));

            } catch (CategoryNotFoundException | CustomerNotFoundException e) {
                System.err.println("Error with parsing " + Arrays.toString(fields));
                System.err.println(e.getMessage());
            }

        }
        return projects;
    }

    private List<String[]> objToArray(List<Project> projects) {
        List<String[]> data = new ArrayList<>();
        for (Project project : projects) {
            String id = String.valueOf(project.getId());
            String name = project.getName();
            String status = String.valueOf(project.getStatus());
            String categories = project.getCategories().stream()
                    .map(c -> String.valueOf(c.getId()))
                    .collect(Collectors.joining(","));
            String customer = String.valueOf(project.getCustomer().getId());
            data.add(new String[]{id, name, status, categories, customer});
        }
        return data;
    }
}
