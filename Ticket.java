import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    public int ticketId;
    public String passengerName;
    public String trainName;
    public String source;
    public String destination;
    public String travelDateTime;

    public Ticket(int ticketId, String passengerName, String trainName, String source, String destination,
            String travelDateTime) {
        this.ticketId = ticketId;
        this.passengerName = passengerName;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.travelDateTime = travelDateTime;
    }

    public static Ticket fromCSV(String line) {
        String[] parts = line.split(",", -1);
        return new Ticket(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5]);
    }

    public String toCSV() {
        return ticketId + "," + passengerName + "," + trainName + "," + source + "," + destination + ","
                + travelDateTime;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId + "\nPassenger: " + passengerName +
                "\nTrain: " + trainName + "\nFrom: " + source + "\nTo: " + destination +
                "\nTravel Date & Time: " + travelDateTime;
    }
}
