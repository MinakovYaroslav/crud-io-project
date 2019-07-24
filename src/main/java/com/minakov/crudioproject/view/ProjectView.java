package com.minakov.crudioproject.view;

import com.minakov.crudioproject.controller.CategoryController;
import com.minakov.crudioproject.controller.CustomerController;
import com.minakov.crudioproject.controller.ProjectController;
import com.minakov.crudioproject.exc.CategoryNotFoundException;
import com.minakov.crudioproject.exc.CustomerNotFoundException;
import com.minakov.crudioproject.exc.ProjectNotFoundException;
import com.minakov.crudioproject.io.Console;
import com.minakov.crudioproject.model.Category;
import com.minakov.crudioproject.model.Customer;
import com.minakov.crudioproject.model.Project;
import com.minakov.crudioproject.model.ProjectStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectView {

    public ProjectView() {
        start();
    }

    private void start() {
        while (true) {
            menu();
            switch (Console.inputLine()) {
                case "1":
                    list();
                    break;
                case "2":
                    create();
                    break;
                case "3":
                    update();
                    break;
                case "4":
                    delete();
                case "5":
                    return;
            }
        }
    }

    private void list() {
        new ProjectController().list().forEach(p -> System.out.println(p.toString()));
    }

    private void delete() {
        try {
            list();
            System.out.println("Enter project id:");
            Long id = Console.inputLong();
            new ProjectController().delete(id);
        } catch (NumberFormatException e) {
            System.err.println(e.toString());
        }
    }

    private void update() {
        try {
            System.out.println("Enter project id:");
            Long id = Long.valueOf(Console.inputLine());
            Project project = new ProjectController().findById(id);

            System.out.println("Enter name or press Enter to continue: ");
            String name = Console.inputLine();
            project.setName(name.equals("") ? project.getName() : name);

            System.out.println("Project finished? (y/n): ");
            if (Console.inputLine().equals("y")) project.setStatus(ProjectStatus.FINISHED);

            Set<Category> categories = categories(id);
            project.setCategories(categories);

            Customer customer = customer(id);
            project.setCustomer(customer);

            new ProjectController().update(project);
        } catch (ProjectNotFoundException | NumberFormatException | CategoryNotFoundException | CustomerNotFoundException e) {
            System.err.println(e.toString());
        }

    }

    private void create() {
        try {
            System.out.println("Enter name: ");
            String name = Console.inputLine();

            Set<Category> categories = categories(null);
            Customer customer = customer(null);

            new ProjectController().create(new Project(name, ProjectStatus.ACTIVE, categories, customer));

        } catch (NumberFormatException | CategoryNotFoundException | CustomerNotFoundException | ProjectNotFoundException e) {
            System.err.println(e.toString());
        }
    }

    private Set<Category> categories(Long id) throws CategoryNotFoundException, NumberFormatException, ProjectNotFoundException {
        List<Category> categories = new CategoryController().list();
        if (categories.isEmpty()) throw new CategoryNotFoundException("Category list is empty!");
        categories.forEach(c -> System.out.println(c.toString()));
        Set<Category> selected = new HashSet<>();
        while (true) {
            System.out.println("Select category id or press Enter to continue:");
            String ctgId = Console.inputLine();
            if (ctgId.equals("")) {
                selected = selected.isEmpty() ? id == null ? selected : new ProjectController().findById(id).getCategories() : selected;
                if (selected.isEmpty()) throw new CategoryNotFoundException("No categories selected!");
                break;
            } else selected.add(new CategoryController().findById(Long.valueOf(ctgId)));
        }
        return selected;
    }

    private Customer customer(Long id) throws CustomerNotFoundException, NumberFormatException, ProjectNotFoundException {
        List<Customer> customers = new CustomerController().list();
        if (customers.isEmpty()) throw new CustomerNotFoundException("Customer list is empty!");
        customers.forEach(c -> System.out.println(c.toString()));
        Customer customer = id == null ? null : new ProjectController().findById(id).getCustomer();
        System.out.println("Select customer id or press Enter to continue:");
        String customerId = Console.inputLine();
        customer = customerId.equals("") ? customer : new CustomerController().findById(Long.valueOf(customerId));
        if (customer == null) throw new CustomerNotFoundException("No customer selected!");
        return customer;
    }

    private void menu() {
        System.out.println();
        System.out.println("1 - Project list");
        System.out.println("2 - Create project");
        System.out.println("3 - Update project");
        System.out.println("4 - Delete project");
        System.out.println("5 - Back");
        System.out.println();
    }
}
