package main.backend.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    /**
     * create file directory if no directory
     */
    public static void createDirectoryIfNotExists(String path) {
        File directory = new File(path);

        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * create file
     */
    public static void createFileIfNotExists(String baseDirectory, String fileName) throws IOException {
        String fullPath = joinPathString(baseDirectory, fileName);
        File file = new File(fullPath);

        if (!file.exists()) {
            file.createNewFile();
        }
    }


    public static String joinPathString(String baseDirectory, String fileName) {
        return String.join(File.separator, baseDirectory, fileName);
    }

    public static Path joinPath(String baseDirectory, String fileName) {
        return Paths.get(joinPathString(baseDirectory, fileName));
    }
}
