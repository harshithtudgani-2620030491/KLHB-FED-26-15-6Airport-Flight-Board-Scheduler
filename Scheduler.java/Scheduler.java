public class Scheduler {
    // CO1: constants
    private static final int RUNWAYS = 3;
    private static final int GAP_MINUTES = 10;

    private FlightBoard board;   // CO4: composition (Scheduler has a FlightBoard)

    public Scheduler(FlightBoard board) {
        this.board = board;
    }

    // CO5: charAt, length, substring; CO2: if; loop over characters
    private static boolean isValidTime(String t) {
        if (t.length() != 5 || t.charAt(2) != ':') {
            return false;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                continue;
            }
            if (!Character.isDigit(t.charAt(i))) {
                return false;
            }
        }
        int h = Integer.parseInt(t.substring(0, 2));
        int m = Integer.parseInt(t.substring(3, 5));
        return h < 24 && m < 60;   // CO1: relational and logical operators
    }

    // CO1: / and % operators; CO5: String.format
    private static String formatTime(int minutes) {
        return String.format("%02d:%02d", minutes / 60, minutes % 60);
    }

    // CO5: for-each loop over the ArrayList; CO1: Math.abs
    private boolean runwayFree(int runway, int minutes, Flight ignore) {
        for (Flight f : board.getFlights()) {
            if (f == ignore || f.getStatus().equals(FlightStatus.CANCELLED) || f.getRunway() != runway) {
                continue;
            }
            if (Math.abs(f.getMinutes() - minutes) < GAP_MINUTES) {
                return false;
            }
        }
        return true;
    }

    private int findFreeRunway(int minutes, Flight ignore) {
        for (int r = 1; r <= RUNWAYS; r++) {
            if (runwayFree(r, minutes, ignore)) {
                return r;
            }
        }
        return -1;
    }

    // CO2: if-else chain; CO5: trim, toUpperCase, isEmpty
    public String addFlight(String flightNo, String airline, String destination, String time) {
        String no = flightNo.trim().toUpperCase();
        if (no.isEmpty() || airline.trim().isEmpty() || destination.trim().isEmpty()) {
            return "Flight number, airline and destination cannot be empty.";
        }
        if (board.find(no) != null) {
            return "A flight with number " + no + " already exists.";
        }
        if (!isValidTime(time)) {
            return "Invalid time. Use 24-hour format like 14:30.";
        }
        int minutes = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3, 5));
        int runway = findFreeRunway(minutes, null);
        if (runway == -1) {
            return "No runway available at that time.";
        }
        board.add(new Flight(no, airline.trim(), destination.trim(), time, runway, FlightStatus.SCHEDULED));
        board.save();
        return "Flight " + no + " scheduled on runway " + runway + ".";
    }

    public String delayFlight(String flightNo, int delay) {
        Flight f = board.find(flightNo);
        if (f == null) {
            return "Flight not found.";
        }
        if (f.getStatus().equals(FlightStatus.CANCELLED)) {
            return "Cannot delay a cancelled flight.";
        }
        if (delay <= 0) {
            return "Delay must be positive.";
        }
        int newMinutes = f.getMinutes() + delay;
        if (newMinutes >= 24 * 60) {
            return "Delay pushes the flight past midnight.";
        }
        int runway = findFreeRunway(newMinutes, f);
        if (runway == -1) {
            return "No runway free at the delayed time.";
        }
        f.delayTo(formatTime(newMinutes), runway);
        board.save();
        return "Flight " + f.getFlightNo() + " delayed to " + f.getTime() + " on runway " + runway + ".";
    }

    public String cancelFlight(String flightNo) {
        Flight f = board.find(flightNo);
        if (f == null) {
            return "Flight not found.";
        }
        if (f.getStatus().equals(FlightStatus.CANCELLED)) {
            return "Flight is already cancelled.";
        }
        f.cancel();
        board.save();
        return "Flight " + f.getFlightNo() + " cancelled.";
    }
}

