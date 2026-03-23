package src.com.example.parking_lots;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLot {
    private int levels;
    private SlotManager slotManager;
    private List<Gate> gates;
    private Map<UUID, Ticket> tickets;
    private PriceStrategy priceStrategy;
    public ParkingLot(int levels, SlotManager slotManager, List<Gate> gates, Map<UUID, Ticket> tickets, PriceStrategy priceStrategy) {
        this.levels = levels;
        this.slotManager = slotManager;
        this.gates = gates;
        this.tickets = tickets;
        this.priceStrategy = priceStrategy;
    }
    public void parkVehicle(Vehicle vehicle, Gate gate) {
        Slot slot = slotManager.findSlot(vehicle.getType(), gate);
        if(slot != null) {
            Ticket ticket = new Ticket(LocalDateTime.now(), slot, vehicle);
            tickets.put(ticket.getTicketId(), ticket);
            slot.setOccupied(true);
        } else {
            System.out.println("No available slots for this vehicle type at the moment.");  
        }
    }
    public double exit(UUID ticketId) { 
        Ticket ticket = tickets.get(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket id: " + ticketId);
        }

        double amount = priceStrategy.calculatePrice(ticket);
        slotManager.releaseSlot(ticket.getSlot());
        tickets.remove(ticketId);
        return amount;
    }
    public void slotStatus(List<Slot> slots) {
        for (Slot slot : slots) {
            System.out.println("Slot ID: " + slot.getSlotId() + ", Level: " + slot.getLevel() + ", Type: " + slot.getSlotType() + ", Occupied: " + slot.isOccupied());
        }
    }
}
