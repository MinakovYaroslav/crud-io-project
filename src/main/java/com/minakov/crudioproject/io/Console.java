package com.minakov.crudioproject.io;

import java.util.Scanner;

public class Console {

    private static Scanner s = new Scanner(System.in);

    public static String inputLine() {
        return s.nextLine();
    }

    public static void close() {
        if (s != null) {
            s.close();
        }
    }

    public static Long inputLong() {
        return s.nextLong();
    }
}
