package src.com.example.parking_lots;

import java.util.List;

public class SlotManager {
    private List<Slot> slots;
    private SlotStrategy slotStrategy;

    public SlotManager(List<Slot> slots, SlotStrategy slotStrategy) {
        this.slots = slots;
        this.slotStrategy = slotStrategy;
    }

    public Slot findSlot(VehicleType vehicleType, Gate gate) {
        return slotStrategy.allocateSlot(vehicleType, gate, slots);
    }

    public void releaseSlot(Slot slot) {
        if (slot != null) {
            slot.setOccupied(false);
        }
    }
}