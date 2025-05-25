import java.io.*;
import java.util.*;

public class TicketManager {
    private static final String FILE_NAME = "tickets.csv";
    private List<Ticket> tickets = new ArrayList<>();
    private int ticketCounter = 1;

    public TicketManager() {
        loadTicketsFromFile();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public int getNextTicketId() {
        return ticketCounter++;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        saveTicketsToFile();
    }

    public boolean cancelTicket(int id) {
        Iterator<Ticket> iterator = tickets.iterator();
        while (iterator.hasNext()) {
            Ticket t = iterator.next();
            if (t.ticketId == id) {
                iterator.remove();
                saveTicketsToFile();
                return true;
            }
        }
        return false;
    }

    private void loadTicketsFromFile() {
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

    private void saveTicketsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Ticket t : tickets) {
                writer.println(t.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving tickets to file.");
        }
    }
}
