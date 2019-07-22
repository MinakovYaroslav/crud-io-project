package com.minakov.crudioproject.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtil {

    public static List<String[]> read(String path) {
        try {
            return Files.lines(Paths.get(path)).map(l -> l.split(";")).collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Reading file error: " + e);
        }
        return Collections.emptyList();
    }

    public static void write(List<String[]> data, String path) {
        try {
            Files.write(Paths.get(path), data.stream()
                    .map(f -> String.join(";", f))
                    .collect(Collectors.toList()), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Writing file error: " + e);
        }
    }
}
