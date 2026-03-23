package src.com.example.parking_lots;
import java.util.Map;
import java.util.UUID;

public class Slot {
    private UUID slotId;
    private int level;
    private SlotType slotType;
    private boolean occupied;
    private Map<Gate, Integer> distanceFromGate;
    public Slot(int level, SlotType slotType, Map<Gate, Integer> distanceFromGate) {
        this.slotId = UUID.randomUUID();
        this.level = level;
        this.slotType = slotType;
        this.occupied = false;
        this.distanceFromGate = distanceFromGate;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public boolean isOccupied() {
        return this.occupied;
    }
    public UUID getSlotId() {
        return this.slotId;
    }
    public int getLevel() {
        return this.level;
    }
    public SlotType getSlotType() {
        return this.slotType;
    }
    public Map<Gate, Integer> getDistanceFromGate() {
        return this.distanceFromGate;
    }
}
