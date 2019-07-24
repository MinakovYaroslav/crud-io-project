package com.minakov.crudioproject.view;

import com.minakov.crudioproject.io.Console;

public class DispatcherView {

    public void start() {
        while (true) {
            menu();
            switch (Console.inputLine()) {
                case "1":
                    new CategoryView();
                    break;
                case "2":
                    new CustomerView();
                    break;
                case "3":
                    new ProjectView();
                    break;
                case "exit":
                    Console.close();
                    return;
            }
        }
    }

    private void menu() {
        System.out.println();
        System.out.println("1 - Category menu");
        System.out.println("2 - Customer menu");
        System.out.println("3 - Project menu");
        System.out.println("exit - Program shutdown");
        System.out.println();
    }
}
