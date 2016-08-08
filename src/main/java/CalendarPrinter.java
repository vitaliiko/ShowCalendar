import utils.ConsoleConstants;
import utils.LocaleDateUtils;

import java.time.*;
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
        printPrevMonth(startDayOfWeek);

        int dayOfWeek = startDayOfWeek;
        for (int i = 1; i <= monthLength; i++) {
            printDay(i, dayOfWeek);
            if (dayOfWeek % 7 == 0 && i < monthLength) {
                createNewLine(weekOfYear++);
                dayOfWeek = 0;
            }
            dayOfWeek++;
        }
        printNextMonth(dayOfWeek - 1);
        System.out.println(ConsoleConstants.DEFAULT_COLOR + ConsoleConstants.HORIZONTAL_LINE_DELIMITER);
    }

    private void printPrevMonth(int startDayOfWeek) {
        Month prevMonth;
        int prevMonthLength;
        if (month - 1 == 0) {
            prevMonth = Month.DECEMBER;
            prevMonthLength = prevMonth.maxLength();
        } else {
            prevMonth = Month.of(month - 1);
            prevMonthLength = prevMonth.length(Year.isLeap(year));
        }
        int prevMonthStartDay = prevMonthLength - startDayOfWeek + 2;
        for (int i = prevMonthStartDay; i <= prevMonthLength; i++) {
            System.out.print(ConsoleConstants.OTHER_MONTH_COLOR + i + " " + ConsoleConstants.CELLS_DELIMITER);
        }
    }

    private void printNextMonth(int lastDayOfWeek) {
        if (lastDayOfWeek == 7) {
            return;
        }
        for (int i = 1; i <= 7 - lastDayOfWeek; i++) {
            System.out.print(ConsoleConstants.OTHER_MONTH_COLOR + " " + i + " " + ConsoleConstants.CELLS_DELIMITER);
        }
    }

    private void printTitle(ZonedDateTime zonedDateTime) {
        System.out.println();
        String monthName = zonedDateTime.getMonth().getDisplayName(TextStyle.FULL, LocaleDateUtils.LOCALE);
        System.out.print(ConsoleConstants.MAIN_COLOR + monthName + ", "
                + zonedDateTime.getYear());
        System.out.println(ConsoleConstants.HORIZONTAL_LINE_DELIMITER);
    }

    private void createNewLine(int weekOfYear) {
        System.out.println(ConsoleConstants.DEFAULT_COLOR + ConsoleConstants.HORIZONTAL_LINE_DELIMITER);
        printWeekNumber(weekOfYear);
    }

    private void printWeekNumber(int weekOfYear) {
        System.out.print(ConsoleConstants.WEEK_NUMBER_COLOR + (weekOfYear < 10 ? "  " : " ") + weekOfYear + " "
                + ConsoleConstants.CELLS_DELIMITER + ConsoleConstants.DEFAULT_COLOR);
    }

    private void printDay(int day, int dayOfWeek) {
        String dayOfMonth = String.valueOf(day);
        dayOfMonth = dayOfMonth.length() == 1 ? " " + dayOfMonth : dayOfMonth;
        if (isCurrentDate(day)) {
            dayOfMonth = ConsoleConstants.CURRENT_DATE_COLOR + dayOfMonth;
        } else {
            dayOfMonth = dayOfWeek > 5 ? ConsoleConstants.WEEKEND_COLOR + dayOfMonth
                    : ConsoleConstants.MAIN_COLOR + dayOfMonth;
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

    private void printFirstLine() {
        System.out.print(ConsoleConstants.WEEK_NUMBER_COLOR + "Week" + ConsoleConstants.CELLS_DELIMITER);
        IntStream.rangeClosed(1, 7).forEach(i -> {
            DayOfWeek dayOfWeek = DayOfWeek.of(i);
            String day = dayOfWeek.getDisplayName(TextStyle.SHORT, LocaleDateUtils.LOCALE)
                    + ConsoleConstants.CELLS_DELIMITER;
            System.out.print(i > 5 ? ConsoleConstants.WEEKEND_COLOR + day : ConsoleConstants.MAIN_COLOR + day);
        });
        System.out.println(ConsoleConstants.DEFAULT_COLOR + ConsoleConstants.HORIZONTAL_LINE_DELIMITER);
    }
}
