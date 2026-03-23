package src.com.example.parking_lots;

import java.util.List;

public interface SlotStrategy {
    Slot allocateSlot(VehicleType vehicleType, Gate gate, List<Slot> slots);
}
