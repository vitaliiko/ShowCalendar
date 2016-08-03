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
        int dayOfWeek = startDayOfWeek;
        for (int i = 1; i <= monthLength; i++) {
            String dayOfMonth = String.valueOf(i);
            dayOfMonth = dayOfMonth.length() == 1 ? " " + dayOfMonth : dayOfMonth;
            if (i == 1) {
                System.out.print(startMonthLine);
            }
            System.out.print(dayOfMonth + " | ");
            if (dayOfWeek % 7 == 0) {
                System.out.println();
                dayOfWeek = 0;
                System.out.println("----------------------------------");
            }
            dayOfWeek++;
        }
    }

    private static ZonedDateTime createZonedDateTime(int year, int month) {
        ZoneId zone = ZoneId.of(ZoneId.SHORT_IDS.get("ART"));
        return ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, zone);
    }

    private static String createStartMonthLine(int startDayOfWeek) {
        String startLine = "";
        for (int i = 0; i < startDayOfWeek - 1; i++) {
            startLine += "     ";
        }
        return startLine;
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
