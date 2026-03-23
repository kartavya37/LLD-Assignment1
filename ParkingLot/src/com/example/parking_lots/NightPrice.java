package src.com.example.parking_lots;

import java.time.LocalDateTime;

public class NightPrice implements PriceStrategy {
    public double calculatePrice(Ticket ticket) {
        LocalDateTime cur = LocalDateTime.now();
        int totalHours = cur.getHour() - ticket.getEntryTime().getHour();
        return totalHours * 5; // Assuming 10 INR per hour during night time
    }
}
