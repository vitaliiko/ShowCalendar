import utils.ConsoleConstants;
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

        printTitle(zonedDateTime);
        printFirstLine();
        printWeekNumber(weekOfYear++);
        printStartMonthLine(startDayOfWeek);

        int dayOfWeek = startDayOfWeek;
        for (int i = 1; i <= monthLength; i++) {
            printDay(i, dayOfWeek);
            if (dayOfWeek % 7 == 0 && i < monthLength) {
                createNewLine(weekOfYear++);
                dayOfWeek = 0;
            }
            dayOfWeek++;
        }
        System.out.println(ConsoleConstants.DEFAULT_COLOR + ConsoleConstants.HORIZONTAL_LINE_DELIMITER);
    }

    private void printTitle(ZonedDateTime zonedDateTime) {
        System.out.println();
        System.out.print(zonedDateTime.getMonth().getDisplayName(TextStyle.FULL, LocaleDateUtils.LOCALE) + ", "
                + zonedDateTime.getYear());
        System.out.println(ConsoleConstants.HORIZONTAL_LINE_DELIMITER);
    }

    private void createNewLine(int weekOfYear) {
        System.out.println(ConsoleConstants.DEFAULT_COLOR + ConsoleConstants.HORIZONTAL_LINE_DELIMITER);
        printWeekNumber(weekOfYear);
    }

    private void printWeekNumber(int weekOfYear) {
        System.out.print(ConsoleConstants.WEEK_NUMBER_COLOR + " " + weekOfYear + " "
                + ConsoleConstants.CELLS_DELIMITER + ConsoleConstants.DEFAULT_COLOR);
    }

    private void printDay(int day, int dayOfWeek) {
        String dayOfMonth = String.valueOf(day);
        dayOfMonth = dayOfMonth.length() == 1 ? " " + dayOfMonth : dayOfMonth;
        if (isCurrentDate(day)) {
            dayOfMonth = ConsoleConstants.CURRENT_DATE_COLOR + dayOfMonth;
        } else {
            dayOfMonth = dayOfWeek > 5 ? ConsoleConstants.WEEKEND_COLOR + dayOfMonth
                    : ConsoleConstants.DEFAULT_COLOR + dayOfMonth;
        }
        System.out.print(dayOfMonth + " " + ConsoleConstants.CELLS_DELIMITER);
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

    private void printStartMonthLine(int startDayOfWeek) {
        final String[] startLine = {""};
        IntStream.rangeClosed(1, startDayOfWeek - 1)
                .forEach(i -> startLine[0] += ConsoleConstants.EMPTY_SPACE);
        System.out.print(startLine[0]);
    }

    private void printFirstLine() {
        System.out.print(ConsoleConstants.WEEK_NUMBER_COLOR + "Week" + ConsoleConstants.CELLS_DELIMITER);
        IntStream.rangeClosed(1, 7).forEach(i -> {
            DayOfWeek dayOfWeek = DayOfWeek.of(i);
            String day = dayOfWeek.getDisplayName(TextStyle.SHORT, LocaleDateUtils.LOCALE)
                    + ConsoleConstants.CELLS_DELIMITER;
            System.out.print(i > 5 ? ConsoleConstants.WEEKEND_COLOR + day : ConsoleConstants.DEFAULT_COLOR + day);
        });
        System.out.println(ConsoleConstants.DEFAULT_COLOR + ConsoleConstants.HORIZONTAL_LINE_DELIMITER);
    }
}
