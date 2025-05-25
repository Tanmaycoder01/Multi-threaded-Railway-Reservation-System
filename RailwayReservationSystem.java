import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RailwayReservationSystem {
    private static Scanner sc = new Scanner(System.in);
    private static TicketManager ticketManager = new TicketManager();

    public static void main(String[] args) {
        if (!AdminLogin.login(sc)) {
            System.out.println("Login failed. Exiting program.");
            return;
        }

        while (true) {
            System.out.println("\n--- Railway Reservation System ---");
            System.out.println("1. Book Ticket");
            System.out.println("2. View All Booked Tickets");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    bookTicket();
                    break;
                case 2:
                    viewTickets();
                    break;
                case 3:
                    cancelTicket();
                    break;
                case 4:
                    System.out.println("Thank you for using the system.");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void bookTicket() {
        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Train Name: ");
        String train = sc.nextLine();

        System.out.print("Enter Source Station: ");
        String source = sc.nextLine();

        System.out.print("Enter Destination Station: ");
        String destination = sc.nextLine();

        String travelDateTime = inputTravelDateTime();

        Ticket ticket = new Ticket(
            ticketManager.getNextTicketId(), name, train, source, destination, travelDateTime
        );
        ticketManager.addTicket(ticket);
        System.out.println("Ticket Booked Successfully! Ticket ID: " + ticket.ticketId);
    }

    private static String inputTravelDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (true) {
            System.out.print("Enter Travel Date & Time (yyyy-MM-dd HH:mm): ");
            String input = sc.nextLine();
            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                if (dateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Travel date/time cannot be in the past. Please enter again.");
                    continue;
                }
                return input;
            } catch (Exception e) {
                System.out.println("Invalid format. Please enter date and time in yyyy-MM-dd HH:mm format.");
            }
        }
    }

    private static void viewTickets() {
        if (ticketManager.getTickets().isEmpty()) {
            System.out.println("No tickets booked yet.");
            return;
        }

        for (Ticket t : ticketManager.getTickets()) {
            System.out.println("\n" + t);
        }
    }

    private static void cancelTicket() {
        System.out.print("Enter Ticket ID to cancel: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ticket ID.");
            return;
        }

        boolean cancelled = ticketManager.cancelTicket(id);
        if (cancelled) {
            System.out.println("Ticket ID " + id + " has been cancelled.");
        } else {
            System.out.println("Ticket ID not found.");
        }
    }
}
