package repository.impl;

import exc.CustomerNotFoundException;
import exc.ProjectNotFoundException;
import model.Category;
import model.Customer;
import model.Project;
import model.ProjectStatus;
import repository.ProjectRepository;
import tools.FilePath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectRepositoryImpl implements ProjectRepository {

    @Override
    public Set<Project> findAll() throws CustomerNotFoundException {
        Set<Project> projects = new HashSet<>();
        Path p = Paths.get(FilePath.PROJECTS_FILE);
        try (BufferedReader reader = Files.newBufferedReader(p)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                Set<Category> categories = categories(row[3].split(","));
                projects.add(new Project(Long.valueOf(row[0]), row[1], ProjectStatus.valueOf(row[2]), categories, customer(row[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public Project findById(Long id) throws ProjectNotFoundException, CustomerNotFoundException {
        Path p = Paths.get(FilePath.PROJECTS_FILE);
        try (BufferedReader reader = Files.newBufferedReader(p)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (Long.valueOf(fields[0]).equals(id)) {
                    Set<Category> categories = categories(fields[3].split(","));
                    return new Project(Long.valueOf(fields[0]), fields[1], ProjectStatus.valueOf(fields[2]), categories, customer(fields[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ProjectNotFoundException(String.valueOf(id));
    }

    @Override
    public Project save(Project project) {
        String[] pArray = projectToArray(project);
        try {
            Path p = Paths.get(FilePath.PROJECTS_FILE);
            Path tempFile = Files.createTempFile(p.getParent(), "temp", ".txt");
            try (BufferedReader reader = Files.newBufferedReader(p);
                 BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
                String line;
                Long id = 1L;
                String[] fields;
                Long projectId = null;
                while ((line = reader.readLine()) != null) {
                    fields = line.split(";");
                    projectId = Long.valueOf(fields[0]);
                    if (projectId > id) id = projectId;
                    if (projectId.equals(Long.valueOf(pArray[0]))) {
                        fields[1] = pArray[1];
                        fields[2] = pArray[2];
                        fields[3] = pArray[3];
                        fields[4] = pArray[4];
                    }
                    writer.write(String.join(";", fields));
                }
                if (pArray[0] == null) {
                    project.setId(projectId != null ? id + 1 : id);
                    pArray[0] = String.valueOf(projectId != null ? id + 1 : id);
                    writer.write(String.join(";", pArray));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return project;
    }

    private Set<Category> categories(String[] c) {
        Set<Category> categories = new HashSet<>();
        Path p = Paths.get(FilePath.CATEGORIES_FILE);
        try (BufferedReader reader = Files.newBufferedReader(p)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                for (String id : c) {
                    if (id.equals(row[0])) {
                        Category category = new Category(Long.valueOf(row[0]), row[1]);
                        categories.add(category);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
    }

    private Customer customer(String id) throws CustomerNotFoundException {
        Customer customer = null;
        Path p = Paths.get(FilePath.CUSTOMERS_FILE);
        try (BufferedReader reader = Files.newBufferedReader(p)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                if (row[0].equals(id)) {
                    customer = new Customer(Long.valueOf(row[0]), row[1]);
                    break;
                }
            }
            if (customer == null) throw new CustomerNotFoundException(id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customer;
    }

    private String[] projectToArray(Project p) {
        return new String[]{String.valueOf(p.getId()),
                p.getName(),
                String.valueOf(p.getStatus()),
                p.getCategories().stream()
                        .map(c -> String.valueOf(c.getId()))
                        .collect(Collectors.joining(",")),
                String.valueOf(p.getCustomer().getId())};
    }
}
