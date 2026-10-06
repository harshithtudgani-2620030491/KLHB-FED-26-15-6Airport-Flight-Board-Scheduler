import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FlightBoard {
    private static final int MAX_FLIGHTS = 100;   // CO1: constant (final)

    // CO3: array of objects, plus a counter for how many slots are used
    private Flight[] flights = new Flight[MAX_FLIGHTS];
    private int count = 0;
    private String fileName;

    public FlightBoard(String fileName) {
        this.fileName = fileName;
        load();
    }

    public int getCount() {
        return count;
    }

    public Flight getFlight(int index) {
        return flights[index];
    }

    // CO3: adding to an array; CO2: if
    public boolean add(Flight f) {
        if (count == MAX_FLIGHTS) {
            return false;
        }
        flights[count] = f;
        count++;
        return true;
    }

    // CO3: linear search through an array; CO5: equalsIgnoreCase, trim
    public Flight find(String flightNo) {
        for (int i = 0; i < count; i++) {
            if (flights[i].getFlightNo().equalsIgnoreCase(flightNo.trim())) {
                return flights[i];
            }
        }
        return null;
    }

    public Flight[] getSortedFlights() {
        Flight[] sorted = Arrays.copyOf(flights, count);   // CO5: library algorithm (Arrays.copyOf)

        // CO3: selection sort on an array
        for (int i = 0; i < count - 1; i++) {
            int min = i;
            for (int j = i + 1; j < count; j++) {
                if (sorted[j].getMinutes() < sorted[min].getMinutes()) {
                    min = j;
                }
            }
            Flight temp = sorted[i];
            sorted[i] = sorted[min];
            sorted[min] = temp;
        }
        return sorted;
    }

    // CO3: count the matches first, then build an exact-size array
    public Flight[] searchByDestination(String destination) {
        Flight[] sorted = getSortedFlights();
        int matches = 0;
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i].getDestination().equalsIgnoreCase(destination.trim())) {
                matches++;
            }
        }
        Flight[] result = new Flight[matches];
        int k = 0;
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i].getDestination().equalsIgnoreCase(destination.trim())) {
                result[k] = sorted[i];
                k++;
            }
        }
        return result;
    }

    // CO3: passing an array to a method; CO5: printf formatting
    public void printTable(Flight[] list) {
        if (list.length == 0) {
            System.out.println("No flights to show.");
            return;
        }
        System.out.printf("%-8s %-14s %-14s %-6s %-4s %s%n", "FLIGHT", "AIRLINE", "DESTINATION", "TIME", "RWY", "STATUS");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }
    }

    // CO5: file I/O (reading); try/catch is needed here for IOException
    private void load() {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                Flight f = Flight.fromFileLine(line);
                if (f != null) {
                    add(f);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Could not read " + fileName + ": " + e.getMessage());
        }
    }

    // CO5: file I/O (writing)
    public void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < count; i++) {
                bw.write(flights[i].toFileLine());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Could not save " + fileName + ": " + e.getMessage());
        }
    }
}
