import utils.ConsoleUtils;
import utils.LocaleDateUtils;

import java.time.DayOfWeek;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.stream.IntStream;

public class CalendarPrinter {

    private int year;
    private int month;

    public CalendarPrinter(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public CalendarPrinter(int month) {
        this.year = LocaleDateUtils.CURRENT_DATE.getYear();
        this.month = month;
    }

    public CalendarPrinter() {
        this.year = LocaleDateUtils.CURRENT_DATE.getYear();
        this.month = LocaleDateUtils.CURRENT_DATE.getMonthValue();
    }

    public void printMonth() {
        ZonedDateTime zonedDateTime = createDefaultZonedDateTime();
        int startDayOfWeek = zonedDateTime.getDayOfWeek().getValue();
        boolean isLeap = Year.isLeap(zonedDateTime.getYear());
        int monthLength = zonedDateTime.getMonth().length(isLeap);
        int weekOfYear = zonedDateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        String startMonthLine = createStartMonthLine(startDayOfWeek);

        printTitle(zonedDateTime);
        printFirstLine();
        printWeekNumber(weekOfYear++);
        System.out.print(startMonthLine);

        int dayOfWeek = startDayOfWeek;
        for (int i = 1; i <= monthLength; i++) {
            printDay(i, dayOfWeek);
            if (dayOfWeek % 7 == 0) {
                createNewLine(weekOfYear++);
                dayOfWeek = 0;
            }
            dayOfWeek++;
        }
        System.out.println(ConsoleUtils.HORIZONTAL_LINE_DELIMITER);
    }

    private void printTitle(ZonedDateTime zonedDateTime) {
        System.out.println();
        System.out.print(zonedDateTime.getMonth().getDisplayName(TextStyle.FULL, LocaleDateUtils.LOCALE) + ", "
                + zonedDateTime.getYear());
        System.out.println(ConsoleUtils.HORIZONTAL_LINE_DELIMITER);
    }

    private void createNewLine(int weekOfYear) {
        System.out.println(ConsoleUtils.DEFAULT_COLOR + ConsoleUtils.HORIZONTAL_LINE_DELIMITER);
        printWeekNumber(weekOfYear);
    }

    private void printWeekNumber(int weekOfYear) {
        System.out.print(ConsoleUtils.WEEK_NUMBER_COLOR + weekOfYear + "  | " + ConsoleUtils.DEFAULT_COLOR);
    }

    private void printDay(int day, int dayOfWeek) {
        String dayOfMonth = String.valueOf(day);
        dayOfMonth = dayOfMonth.length() == 1 ? " " + dayOfMonth : dayOfMonth;
        dayOfMonth = dayOfWeek > 5 ? ConsoleUtils.WEEKEND_COLOR + dayOfMonth : ConsoleUtils.DEFAULT_COLOR + dayOfMonth;
        dayOfMonth = isCurrentDate(day) ? ConsoleUtils.CURRENT_DATE_COLOR + dayOfMonth : dayOfMonth;
        System.out.print(dayOfMonth + " | ");
    }

    private boolean isCurrentDate(int day) {
        ZonedDateTime zonedDateTime = createDefaultZonedDateTime(day);
        return zonedDateTime.getYear() == LocaleDateUtils.CURRENT_DATE.getYear()
                && zonedDateTime.getMonthValue() == LocaleDateUtils.CURRENT_DATE.getMonthValue()
                && zonedDateTime.getDayOfMonth() == LocaleDateUtils.CURRENT_DATE.getDayOfMonth();
    }

    private ZonedDateTime createDefaultZonedDateTime() {
        return ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, LocaleDateUtils.ZONE_ID);
    }

    private ZonedDateTime createDefaultZonedDateTime(int day) {
        return ZonedDateTime.of(year, month, day, 0, 0, 0, 0, LocaleDateUtils.ZONE_ID);
    }

    private String createStartMonthLine(int startDayOfWeek) {
        final String[] startLine = {""};
        IntStream.rangeClosed(1, startDayOfWeek - 1).forEach(i -> startLine[0] += "     ");
        return startLine[0];
    }

    private void printFirstLine() {
        System.out.print(ConsoleUtils.WEEK_NUMBER_COLOR + "Week| ");
        IntStream.rangeClosed(1, 7).forEach(i -> {
                DayOfWeek dayOfWeek = DayOfWeek.of(i);
                String day = dayOfWeek.getDisplayName(TextStyle.SHORT, LocaleDateUtils.LOCALE) + "| ";
                System.out.print(i > 5 ? ConsoleUtils.WEEKEND_COLOR + day : ConsoleUtils.DEFAULT_COLOR + day);
        });
        System.out.println(ConsoleUtils.DEFAULT_COLOR + ConsoleUtils.HORIZONTAL_LINE_DELIMITER);
    }
}
