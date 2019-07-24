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

    private static final String FILE_PATH = "C:\\projects\\crud-io-project\\src\\main\\resources\\Projects.txt";

    private List<Project> projects;

    public ProjectRepositoryImpl() {
        this.projects = projects();
    }

    @Override
    public List<Project> findAll() {
        return projects;
    }

    @Override
    public void delete(Long id) {
        try {
            Project project = findById(id);
            project.setStatus(ProjectStatus.DELETED);
            int index = projects.indexOf(project);
            projects.set(index, project);
            List<String[]> data = objToArray(projects);
            IOUtil.write(data, FILE_PATH);
        } catch (ProjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Project findById(Long id) throws ProjectNotFoundException {
        return projects.stream()
                .filter(p -> p.getId().equals(id)).findAny().orElseThrow(() -> new ProjectNotFoundException("Project with id: " + id + " not found"));
    }

    @Override
    public Project create(Project project) {
        try {
            if (project.getCustomer() == null) throw new CustomerNotFoundException("Customer not found!");
            if (project.getCategories().isEmpty()) throw new CategoryNotFoundException("Category not found!");
            long lastId = projects.stream().mapToLong(AbstractIdentifiable::getId).max().orElse(0);
            project.setId(++lastId);
            projects.add(project);
            List<String[]> data = objToArray(projects);
            IOUtil.write(data, FILE_PATH);
        } catch (CategoryNotFoundException | CustomerNotFoundException e) {
            System.err.println("Creation failed: " + e.toString());
        }
        return project;
    }

    @Override
    public Project update(Project project) {
        try {
            Project old = findById(project.getId());
            int index = projects.indexOf(old);
            projects.set(index, project);
            List<String[]> data = objToArray(projects);
            IOUtil.write(data, FILE_PATH);
        } catch (ProjectNotFoundException e) {
            System.err.println(e.getMessage());
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
                ProjectStatus status = ProjectStatus.valueOf(fields[2]);
                if (status == ProjectStatus.ACTIVE) {
                    Long id = Long.valueOf(fields[0]);
                    String name = fields[1];
                    String[] categoriesArray = fields[3].split(",");
                    Set<Category> categories = new HashSet<>();
                    for (String c : categoriesArray) {
                        categories.add(categoryRepository.findById(Long.valueOf(c)));
                    }
                    Customer customer = customerRepository.findById(Long.valueOf(fields[4]));
                    projects.add(new Project(id, name, status, categories, customer));
                }

            } catch (CategoryNotFoundException | CustomerNotFoundException e) {
                System.err.println("Error with parsing " + Arrays.toString(fields));
                System.err.println(e.toString());
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
