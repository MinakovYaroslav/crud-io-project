package com.minakov.crudioproject.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    public static String input() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Console input error: " + e);
        }
        return "";
    }
}
