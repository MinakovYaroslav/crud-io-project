package com.minakov.crudioproject.view;

import com.minakov.crudioproject.controller.CustomerController;
import com.minakov.crudioproject.io.Console;
import com.minakov.crudioproject.model.Customer;

public class CustomerView {

    public CustomerView() {
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
            new CustomerController().delete(id);
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
            Customer c = new Customer(id, name);
            new CustomerController().update(c);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
    }

    private void create() {
        System.out.print("Enter name: ");
        String name = Console.inputLine();
        Customer c = new Customer(name);
        new CustomerController().create(c);
    }

    private void list() {
        new CustomerController().list().forEach(c -> System.out.println(c.toString()));
    }

    private void menu() {
        System.out.println();
        System.out.println("1 - Customer list");
        System.out.println("2 - Create customer");
        System.out.println("3 - Update customer");
        System.out.println("4 - Delete customer");
        System.out.println("5 - Back");
        System.out.println();
    }
}
