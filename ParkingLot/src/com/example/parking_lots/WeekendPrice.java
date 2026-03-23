package src.com.example.parking_lots;

import java.time.LocalDateTime;

public class WeekendPrice implements PriceStrategy {
    public double calculatePrice(Ticket ticket) {
        LocalDateTime cur = LocalDateTime.now();
        int totalHours = cur.getHour() - ticket.getEntryTime().getHour();
        return totalHours * 15; // Assuming 15 INR per hour during weekends
    }
    
}
