package src.com.example.parking_lots;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private UUID ticketId;
    private LocalDateTime entryTime;
    private Slot slot;
    private Vehicle vehicle;
    public Ticket(LocalDateTime entryTime, Slot slot, Vehicle vehicle) {
        this.ticketId = UUID.randomUUID();
        this.entryTime = entryTime;
        this.slot = slot;
        this.vehicle = vehicle;
    }
    public UUID getTicketId() {
        return this.ticketId;
    }
    public LocalDateTime getEntryTime() {
        return this.entryTime;
    }
    public Slot getSlot() {
        return this.slot;
    }
    public Vehicle getVehicle() {
        return this.vehicle;
    }
}
