import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        if (!validateArgs(args)) {
            System.out.println("Error: Wrong arguments");
            return;
        }
        List<Integer> arguments = Arrays.stream(args).map(Integer::valueOf).collect(Collectors.toList());
        CalendarPrinter calendarPrinter;
        if (arguments.size() == 2) {
            calendarPrinter = new CalendarPrinter(arguments.get(0), arguments.get(1));
        } else if (args.length == 1) {
            calendarPrinter = new CalendarPrinter(arguments.get(0));
        } else {
            calendarPrinter = new CalendarPrinter();
        }
        calendarPrinter.printMonth();
    }

    private static boolean validateArgs(String[] args) {
        return Arrays.stream(args).allMatch(StringUtils::isNumeric);
    }
}
