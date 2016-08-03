import org.apache.commons.lang3.StringUtils;
import utils.ConsoleConstants;
import utils.LocaleDateUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            performCalendarPrint(args);
        } catch (IOException e) {
            System.out.println(ConsoleConstants.ERROR_COLOR + e.getMessage());
        }
    }

    private static void performCalendarPrint(String[] args) throws IOException {
        checkArgs(args);
        List<Integer> arguments = Arrays.stream(args).map(Integer::valueOf).collect(Collectors.toList());
        CalendarPrinter calendarPrinter;
        if (arguments.size() == 2) {
            LocaleDateUtils.checkValidYear(arguments.get(0));
            LocaleDateUtils.checkValidMonth(arguments.get(1));
            calendarPrinter = new CalendarPrinter(arguments.get(0), arguments.get(1));
        } else if (args.length == 1) {
            LocaleDateUtils.checkValidMonth(arguments.get(0));
            calendarPrinter = new CalendarPrinter(arguments.get(0));
        } else {
            calendarPrinter = new CalendarPrinter();
        }
        calendarPrinter.printMonth();
    }

    private static void checkArgs(String[] args) throws IOException {
        if (!Arrays.stream(args).allMatch(StringUtils::isNumeric)) {
            throw new IOException("Error: Wrong arguments");
        }
    }
}
