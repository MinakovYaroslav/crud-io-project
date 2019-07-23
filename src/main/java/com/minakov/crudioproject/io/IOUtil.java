package com.minakov.crudioproject.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtil {

    public static List<String[]> read(String path) {
        try {
            return Files.lines(Paths.get(path)).map(l -> l.split(";")).collect(Collectors.toList());
        } catch (NoSuchFileException ignore) {
        } catch (IOException e) {
            System.err.println("Reading file error: " + e);
        }
        return Collections.emptyList();
    }

    public static void write(List<String[]> data, String path) {
        try {
            Files.write(Paths.get(path), data.stream()
                    .map(fields -> String.join(";", fields))
                    .collect(Collectors.toList()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Writing file error: " + e);
        }
    }
}
