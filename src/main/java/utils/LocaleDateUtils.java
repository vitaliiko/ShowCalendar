package utils;

import java.io.IOException;
import java.time.DateTimeException;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

public class LocaleDateUtils {

    public static void checkValidYear(int year) throws IOException {
        try {
            YEAR.checkValidValue(year);
        } catch (DateTimeException e) {
            throw new IOException("Invalid year", e);
        }
    }

    public static void checkValidMonth(int month) throws IOException {
        try {
            MONTH_OF_YEAR.checkValidValue(month);
        } catch (DateTimeException e) {
            throw new IOException("Invalid month", e);
        }
    }
}
