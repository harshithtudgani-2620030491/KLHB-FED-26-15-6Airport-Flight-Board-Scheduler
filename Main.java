import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);

    // CO5: reading a line and trim()
    private static String ask(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    // CO5: checks a String is all digits, so parseInt never fails
    private static boolean isNumber(String s) {
        if (s.isEmpty() || s.length() > 6) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // CO1: System.out.println
    private static void printMenu() {
        System.out.println();
        System.out.println("===== AIRPORT FLIGHT SCHEDULER =====");
        System.out.println("1. Add flight");
        System.out.println("2. View schedule");
        System.out.println("3. Search by destination");
        System.out.println("4. Delay flight");
        System.out.println("5. Cancel flight");
        System.out.println("0. Exit");
    }

    public static void main(String[] args) {
        // CO4: creating objects and passing one object into another
        FlightBoard board = new FlightBoard("flights.txt");
        Scheduler scheduler = new Scheduler(board);
        boolean running = true;   // CO1: boolean variable

        while (running) {   // CO2: loop
            printMenu();
            String choice = ask("Choose an option: ");

            // CO2: switch-case
            switch (choice) {
                case "1":
                    String no = ask("Flight number: ");
                    String airline = ask("Airline: ");
                    String dest = ask("Destination: ");
                    String time = ask("Departure time (HH:mm): ");
                    System.out.println(scheduler.addFlight(no, airline, dest, time));
                    break;
                case "2":
                    board.printTable(board.getSortedFlights());
                    break;
                case "3":
                    board.printTable(board.searchByDestination(ask("Destination: ")));
                    break;
                case "4":
                    String delayNo = ask("Flight number: ");
                    String delayText = ask("Delay in minutes: ");
                    if (isNumber(delayText)) {   // CO2: if-else
                        int minutes = Integer.parseInt(delayText);
                        System.out.println(scheduler.delayFlight(delayNo, minutes));
                    } else {
                        System.out.println("Please enter a valid number.");
                    }
                    break;
                case "5":
                    System.out.println(scheduler.cancelFlight(ask("Flight number: ")));
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        in.close();
    }
}
