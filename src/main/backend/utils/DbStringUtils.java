package main.backend.utils;

public class DbStringUtils {
    /**
     * util functions to join & split text file into class */

    public static String defaultDbJoin(String... strings) {
        return String.join("|| ", strings) + "\n";
    }

    public static String[] defaultDbSplit(String strings) {
        return strings.split("\\|\\| ");
    }
}
