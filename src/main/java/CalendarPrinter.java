import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.IntStream;

public class CalendarPrinter {

    public static final Locale LOCALE = Locale.ENGLISH;
    public static final String HORIZONTAL_LINE_SPLITTER = "\n---------------------------------------";

    public static void printMonth(int year, int month) {
        ZonedDateTime zonedDateTime = createZonedDateTime(year, month);
        int startDayOfWeek = zonedDateTime.getDayOfWeek().getValue();
        boolean isLeap = Year.isLeap(zonedDateTime.getYear());
        int monthLength = zonedDateTime.getMonth().length(isLeap);
        final int[] weekOfYear = {zonedDateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)};
        String startMonthLine = createStartMonthLine(startDayOfWeek);

        printDaysOfWeek();
        final int[] dayOfWeek = {startDayOfWeek};
        IntStream.rangeClosed(1, monthLength).forEach(i -> {
            if (i == 1) {
                printWeekNumber(weekOfYear);
                System.out.print(startMonthLine);
            }
            printDay(i);
            if (dayOfWeek[0] % 7 == 0) {
                createNewLine(weekOfYear, dayOfWeek);
            }
            dayOfWeek[0]++;
        });
    }

    private static void createNewLine(int[] weekOfYear, int[] dayOfWeek) {
        dayOfWeek[0] = 0;
        System.out.println(HORIZONTAL_LINE_SPLITTER);
        printWeekNumber(weekOfYear);
    }

    private static void printWeekNumber(int[] weekOfYear) {
        System.out.print(weekOfYear[0] + "  | ");
        weekOfYear[0]++;
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
        System.out.print("Week| ");
        IntStream.rangeClosed(1, 7).forEach(i -> {
                DayOfWeek dayOfWeek = DayOfWeek.of(i);
                String day = dayOfWeek.getDisplayName(TextStyle.SHORT, LOCALE) + "| ";
                System.out.print(day);
        });
        System.out.println(HORIZONTAL_LINE_SPLITTER);
    }
}
