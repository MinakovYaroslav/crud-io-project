package com.minakov.crudioproject.view;

import com.minakov.crudioproject.controller.CategoryController;
import com.minakov.crudioproject.io.Console;
import com.minakov.crudioproject.model.Category;

public class CategoryView {

    public CategoryView() {
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

    private void delete() {
        try {
            list();
            System.out.print("Enter id: ");
            Long id = Console.inputLong();
            new CategoryController().delete(id);
        } catch (NumberFormatException e) {
            System.err.println(e.toString());
        }
    }

    private void update() {
        try {
            System.out.print("Enter id: ");
            Long id = Console.inputLong();
            System.out.print("Enter new name: ");
            String name = Console.inputLine();
            Category c = new Category(id, name);
            new CategoryController().update(c);
        } catch (NumberFormatException e) {
            System.err.println(e.toString());
        }
    }

    private void create() {
        System.out.print("Enter name: ");
        String name = Console.inputLine();
        Category c = new Category(name);
        new CategoryController().create(c);
    }

    private void list() {
        new CategoryController().list().forEach(c -> System.out.println(c.toString()));
    }

    private void menu() {
        System.out.println();
        System.out.println("1 - Category list");
        System.out.println("2 - Create category");
        System.out.println("3 - Update category");
        System.out.println("4 - Delete category");
        System.out.println("5 - Back");
        System.out.println();
    }
}
