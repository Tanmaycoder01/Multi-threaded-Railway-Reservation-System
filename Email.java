import java.io.; import java.time.LocalDateTime; import java.time.format.DateTimeFormatter; import java.util.;

// Data class for Ticket class Ticket { int ticketId; String passengerName; String trainName; String source; String destination; String travelDateTime;

public Ticket(int ticketId, String passengerName, String trainName, String source, String destination, String travelDateTime) {
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
        parts[1], parts[2], parts[3], parts[4], parts[5]
    );
}

public String toCSV() {
    return ticketId + "," + passengerName + "," + trainName + "," + source + "," + destination + "," + travelDateTime;
}

@Override
public String toString() {
    return "Ticket ID: " + ticketId + "\nPassenger: " + passengerName +
           "\nTrain: " + trainName + "\nFrom: " + source + "\nTo: " + destination +
           "\nTravel Date & Time: " + travelDateTime;
}

}

class TicketService { private final List<Ticket> tickets = new ArrayList<>(); private final String FILE_NAME = "data/tickets.csv"; private int ticketCounter = 1;

public TicketService() {
    loadTickets();
}

public void bookTicket(String name, String train, String source, String dest, String travelDateTime) {
    Ticket ticket = new Ticket(ticketCounter++, name, train, source, dest, travelDateTime);
    tickets.add(ticket);
    saveTickets();
    System.out.println("Ticket Booked Successfully! Ticket ID: " + ticket.ticketId);
}

public void cancelTicket(int id) {
    Iterator<Ticket> iterator = tickets.iterator();
    boolean found = false;
    while (iterator.hasNext()) {
        Ticket t = iterator.next();
        if (t.ticketId == id) {
            iterator.remove();
            found = true;
            break;
        }
    }
    if (found) {
        saveTickets();
        System.out.println("Ticket cancelled successfully.");
    } else {
        System.out.println("Ticket ID not found.");
    }
}

public void viewTickets() {
    if (tickets.isEmpty()) {
        System.out.println("No tickets booked yet.");
    } else {
        for (Ticket t : tickets) {
            System.out.println("\n" + t);
        }
    }
}

private void loadTickets() {
    File file = new File(FILE_NAME);
    if (!file.exists()) return;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        int maxId = 0;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            Ticket t = Ticket.fromCSV(line);
            tickets.add(t);
            if (t.ticketId > maxId) maxId = t.ticketId;
        }
        ticketCounter = maxId + 1;
    } catch (IOException e) {
        System.out.println("Error reading ticket data.");
    }
}

private void saveTickets() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
        for (Ticket t : tickets) {
            writer.println(t.toCSV());
        }
    } catch (IOException e) {
        System.out.println("Error saving tickets.");
    }
}

}

public class RailwayReservationSystem { private static final Scanner sc = new Scanner(System.in); private static final String ADMIN_USERNAME = "admin"; private static final String ADMIN_PASSWORD = "password123";

public static void main(String[] args) {
    if (!adminLogin()) {
        System.out.println("Login failed. Exiting.");
        return;
    }

    TicketService service = new TicketService();

    while (true) {
        System.out.println("\n--- Railway Reservation Menu ---");
        System.out.println("1. Book Ticket\n2. View Tickets\n3. Cancel Ticket\n4. Exit");
        System.out.print("Choose an option: ");
        int choice = safeIntInput();

        switch (choice) {
            case 1 -> bookTicket(service);
            case 2 -> service.viewTickets();
            case 3 -> {
                System.out.print("Enter Ticket ID to cancel: ");
                service.cancelTicket(safeIntInput());
            }
            case 4 -> {
                System.out.println("Thank you for using the system.");
                return;
            }
            default -> System.out.println("Invalid option.");
        }
    }
}

private static boolean adminLogin() {
    System.out.println("--- Admin Login ---");
    System.out.print("Username: ");
    String username = sc.nextLine();
    System.out.print("Password: ");
    String password = sc.nextLine();
    return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
}

private static void bookTicket(TicketService service) {
    System.out.print("Enter Passenger Name: ");
    String name = sc.nextLine().trim();
    if (name.isEmpty()) {
        System.out.println("Name cannot be empty.");
        return;
    }

    System.out.print("Enter Train Name: ");
    String train = sc.nextLine().trim();
    System.out.print("Enter Source Station: ");
    String source = sc.nextLine().trim();
    System.out.print("Enter Destination Station: ");
    String dest = sc.nextLine().trim();

    if (source.equalsIgnoreCase(dest)) {
        System.out.println("Source and destination cannot be the same.");
        return;
    }

    String travelDateTime = inputTravelDateTime();
    service.bookTicket(name, train, source, dest, travelDateTime);
}

private static String inputTravelDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    while (true) {
        System.out.print("Enter Travel Date & Time (yyyy-MM-dd HH:mm): ");
        String input = sc.nextLine();
        try {
            LocalDateTime dt = LocalDateTime.parse(input, formatter);
            if (dt.isBefore(LocalDateTime.now())) {
                System.out.println("Date/time cannot be in the past.");
                continue;
            }
            return input;
        } catch (Exception e) {
            System.out.println("Invalid format. Try again.");
        }
    }
}

private static int safeIntInput() {
    try {
        return Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("Invalid number.");
        return -1;
    }
}

}