package main.utils;

public class StringUtils {
    public static boolean isBlank(String str) {
        if (str == null)
            return true;

        return str.isBlank();
    }

    public static boolean isInteger(String str) {
        return str.matches("-?(0|[1-9]\\d*)");
    }
}
