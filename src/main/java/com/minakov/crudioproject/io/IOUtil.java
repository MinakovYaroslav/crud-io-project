package com.minakov.crudioproject.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {

    public static List<String[]> read(String path) {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line.split(";"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static void write(List<String[]> data, String path) {
        try {
            Path p = Paths.get(path);
            Path tempFile = Files.createTempFile(p.getParent(), "temp", ".txt");
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
                for (String[] fields : data) {
                    writer.write(String.join(";", fields));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
