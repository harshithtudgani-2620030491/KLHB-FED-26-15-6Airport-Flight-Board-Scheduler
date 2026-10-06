public class Flight {
    // CO4: class, private fields (encapsulation)
    // CO1: int and String variables
    private String flightNo;
    private String airline;
    private String destination;
    private String time;
    private int runway;
    private String status;

    // CO4: constructor and this
    public Flight(String flightNo, String airline, String destination, String time, int runway, String status) {
        this.flightNo = flightNo;
        this.airline = airline;
        this.destination = destination;
        this.time = time;
        this.runway = runway;
        this.status = status;
    }

    // CO4: getters
    public String getFlightNo() {
        return flightNo;
    }

    public String getAirline() {
        return airline;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() {
        return time;
    }

    public int getRunway() {
        return runway;
    }

    public String getStatus() {
        return status;
    }

    public int getMinutes() {
        String[] parts = time.split(":");   // CO5: split(), CO3: String array
        // CO1: arithmetic and Integer.parseInt
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    // CO4: methods that change the object's state
    public void delayTo(String newTime, int newRunway) {
        this.time = newTime;
        this.runway = newRunway;
        this.status = FlightStatus.DELAYED;
    }

    public void cancel() {
        this.status = FlightStatus.CANCELLED;
    }

    // CO5: StringBuilder
    public String toFileLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(flightNo).append(",");
        sb.append(airline).append(",");
        sb.append(destination).append(",");
        sb.append(time).append(",");
        sb.append(runway).append(",");
        sb.append(status);
        return sb.toString();
    }

    // CO4: static method; CO2: if; CO5: split()
    public static Flight fromFileLine(String line) {
        String[] p = line.split(",");
        if (p.length != 6) {
            return null;
        }
        return new Flight(p[0], p[1], p[2], p[3], Integer.parseInt(p[4]), p[5]);
    }

    // CO4: method overriding; CO5: String.format
    @Override
    public String toString() {
        return String.format("%-8s %-14s %-14s %-6s R%-3d %s", flightNo, airline, destination, time, runway, status);
    }
}
      
            
                
   
 
