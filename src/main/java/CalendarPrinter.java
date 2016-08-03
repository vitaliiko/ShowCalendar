import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.IntStream;

public class CalendarPrinter {

    public static final Locale LOCALE = Locale.ENGLISH;

    public static void printMonth(int year, int month) {
        ZonedDateTime zonedDateTime = createZonedDateTime(year, month);
        int startDayOfWeek = zonedDateTime.getDayOfWeek().getValue();
        int monthLength = zonedDateTime.getMonth().length(true);
        String startMonthLine = createStartMonthLine(startDayOfWeek);

        printDaysOfWeek();
        final int[] dayOfWeek = {startDayOfWeek};
        IntStream.rangeClosed(1, monthLength).forEach(i -> {
            if (i == 1) {
                System.out.print(startMonthLine);
            }
            printDay(i);
            if (dayOfWeek[0] % 7 == 0) {
                System.out.println();
                dayOfWeek[0] = 0;
                System.out.println("----------------------------------");
            }
            dayOfWeek[0]++;
        });
    }

    private static void printDay(int i) {
        String dayOfMonth = String.valueOf(i);
        dayOfMonth = dayOfMonth.length() == 1 ? " " + dayOfMonth : dayOfMonth;
        System.out.print(dayOfMonth + " | ");
    }

    private static ZonedDateTime createZonedDateTime(int year, int month) {
        ZoneId zone = ZoneId.of(ZoneId.SHORT_IDS.get("ART"));
        return ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, zone);
    }

    private static String createStartMonthLine(int startDayOfWeek) {
        final String[] startLine = {""};
        IntStream.rangeClosed(1, startDayOfWeek - 1).forEach(i -> startLine[0] += "     ");
        return startLine[0];
    }

    private static void printDaysOfWeek() {
        IntStream.rangeClosed(1, 7).forEach(i -> {
                DayOfWeek dayOfWeek = DayOfWeek.of(i);
                String day = dayOfWeek.getDisplayName(TextStyle.SHORT, LOCALE) + "| ";
                System.out.print(day);
        });
        System.out.println();
    }
}
