import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.IntStream;

public class CalendarPrinter {

    public static final Locale LOCALE = Locale.ENGLISH;
    public static final ZoneId ZONE_ID = ZoneId.of(ZoneId.SHORT_IDS.get("ART"));
    public static final ZonedDateTime currentDate = ZonedDateTime.now(ZONE_ID);

    public static final String HORIZONTAL_LINE_DELIMITER = "\n----------------------------------------";
    public static final String WEEKEND_COLOR = "\u001B[31m";
    public static final String CURRENT_DATE_COLOR = "\u001B[36m";
    public static final String DEFAULT_COLOR = "\u001B[0m";
    public static final String WEEK_NUMBER_COLOR = "\u001B[32m";

    public static void printMonth(int year, int month) {
        ZonedDateTime zonedDateTime = createZonedDateTime(year, month);
        int startDayOfWeek = zonedDateTime.getDayOfWeek().getValue();
        boolean isLeap = Year.isLeap(zonedDateTime.getYear());
        int monthLength = zonedDateTime.getMonth().length(isLeap);
        int weekOfYear = zonedDateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        String startMonthLine = createStartMonthLine(startDayOfWeek);

        printFirstLine();
        int dayOfWeek = startDayOfWeek;
        for (int i = 1; i <= monthLength; i++) {
            if (i == 1) {
                weekOfYear = printWeekNumber(weekOfYear);
                System.out.print(startMonthLine);
            }
            printDay(i, dayOfWeek);
            if (dayOfWeek % 7 == 0) {
                createNewLine(weekOfYear);
                dayOfWeek = 0;
            }
            dayOfWeek++;
        }
    }

    private static void createNewLine(int weekOfYear) {
        System.out.println(DEFAULT_COLOR + HORIZONTAL_LINE_DELIMITER);
        printWeekNumber(weekOfYear);
    }

    private static int printWeekNumber(int weekOfYear) {
        System.out.print(WEEK_NUMBER_COLOR + weekOfYear + "  | " + DEFAULT_COLOR);
        return weekOfYear++;
    }

    private static void printDay(int i, int dayOfWeek) {
        String dayOfMonth = String.valueOf(i);
        dayOfMonth = dayOfMonth.length() == 1 ? " " + dayOfMonth : dayOfMonth;
        dayOfMonth = dayOfWeek > 5 ? WEEKEND_COLOR + dayOfMonth : DEFAULT_COLOR + dayOfMonth;
        System.out.print(dayOfMonth + " | ");
    }

    private static ZonedDateTime createZonedDateTime(int year, int month) {
        return ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, ZONE_ID);
    }

    private static ZonedDateTime createZonedDateTime(int year, int month, int day) {
        return ZonedDateTime.of(year, month, day, 0, 0, 0, 0, ZONE_ID);
    }

    private static String createStartMonthLine(int startDayOfWeek) {
        final String[] startLine = {""};
        IntStream.rangeClosed(1, startDayOfWeek - 1).forEach(i -> startLine[0] += "     ");
        return startLine[0];
    }

    private static void printFirstLine() {
        System.out.print(WEEK_NUMBER_COLOR + "Week| ");
        IntStream.rangeClosed(1, 7).forEach(i -> {
                DayOfWeek dayOfWeek = DayOfWeek.of(i);
                String day = dayOfWeek.getDisplayName(TextStyle.SHORT, LOCALE) + "| ";
                System.out.print(i > 5 ? WEEKEND_COLOR + day : DEFAULT_COLOR + day);
        });
        System.out.println(DEFAULT_COLOR + HORIZONTAL_LINE_DELIMITER);
    }
}
