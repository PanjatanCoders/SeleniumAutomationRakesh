
void main() {
//    String result = printMessage1("Sunday");
//    IO.println("Result is: " + result);
    IO.println(getDayOfWeek("Saturday"));
}

void printMessage(String dayOfWeek) {
    switch (dayOfWeek) {
        case "Monday":
            System.out.println("Monday");
            break;
        case "Tuesday":
            System.out.println("Tuesday");
            break;
        case "Wednesday":
            System.out.println("Wednesday");
            break;
        case "Thursday":
            System.out.println("Thursday");
            break;
        case "Friday":
            System.out.println("Friday");
            break;
        case "Saturday":
            System.out.println("Saturday");
            break;
        case "Sunday":
            System.out.println("Sunday");
            break;
        default:
            System.out.println("Invalid day of week");
    }

    IO.println("Out of Switch");
}

String printMessage1(String dayOfWeek) {
    String str = "";

    switch (dayOfWeek) {
        case "Monday":
        case "Tuesday":
        case "Wednesday":
//            IO.println("Mid of weekdays");
            str = "Mid of weekdays";
            break;
        case "Thursday":
        case "Friday":
//            System.out.println("Weekdays");
            str = "Weekdays";
            break;
        case "Saturday":
        case "Sunday":
//            System.out.println("Enjoy your weekend");
            str = "Enjoy your weekend";
            break;
        default:
//            System.out.println("Invalid day of week");
            str = "Invalid day of week";
    }

    IO.println("Out of Switch");
    return str;
}

//

String getDayOfWeek(String dayOfWeek) {
    String str = switch (dayOfWeek) {
        case "Monday" -> "Monday";
        case "Tuesday" -> "Tuesday";
        case "Wednesday" -> "Wednesday";
        case "Thursday" -> "Thursday";
        case "Friday" -> "Friday";
        case "Saturday" -> "Saturday";
        case  "Sunday" -> "Sunday";
        default -> "Invalid day of week";
    };

    return str;
}

// continue
// break
// ternary operator