package main.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    /**
     * date format to yyyy-mm-dd*/
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** convert string text to date
     */
    public static LocalDate stringToDate(String str) {
        return LocalDate.parse(str, DATE_TIME_FORMATTER);
    }

    /** convert date to string type
     */
    public static String dateToString(LocalDate date) {
        return DATE_TIME_FORMATTER.format(date);
    }

    /** validate date between start & end
     */
    public static boolean isBetween(LocalDate target, LocalDate start, LocalDate end) {
        return target.compareTo(start) > 0 && target.compareTo(end) < 0;
    }

    /** validate is the date overlapped between start & end
     */
    public static boolean isOverlapped(LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1) {
        return isBetween(startDate, startDate1, endDate1) || isBetween(endDate, startDate1, endDate1)
                || isBetween(startDate1, startDate, endDate) || isBetween(endDate1, startDate, endDate)
                || (startDate.compareTo(startDate1) == 0 && endDate.compareTo(endDate1) == 0);
    }
}
