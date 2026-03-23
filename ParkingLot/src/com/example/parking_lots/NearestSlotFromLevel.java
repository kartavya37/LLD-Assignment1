package src.com.example.parking_lots;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NearestSlotFromLevel implements SlotStrategy {
    @Override
    public Slot allocateSlot(VehicleType vehicleType, Gate gate, List<Slot> slots) {
        Set<SlotType> allowedSlotTypes = getAllowedSlotTypes(vehicleType);
        Slot bestSlot = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Slot slot : slots) {
            if (slot.isOccupied() || !allowedSlotTypes.contains(slot.getSlotType())) {
                continue;
            }

            int distance = getDistanceForGate(slot.getDistanceFromGate(), gate);
            if (bestSlot == null || distance < bestDistance) {
                bestSlot = slot;
                bestDistance = distance;
            }
        }

        return bestSlot;
    }

    private Set<SlotType> getAllowedSlotTypes(VehicleType vehicleType) {
        switch (vehicleType) {
            case TWO_WHEELER:
                return EnumSet.of(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE);
            case CARS:
                return EnumSet.of(SlotType.MEDIUM, SlotType.LARGE);
            case BUSES:
                return EnumSet.of(SlotType.LARGE);
            default:
                return EnumSet.noneOf(SlotType.class);
        }
    }

    private int getDistanceForGate(Map<Gate, Integer> distanceFromGate, Gate gate) {
        if (distanceFromGate == null) {
            return Integer.MAX_VALUE;
        }

        Integer distance = distanceFromGate.get(gate);
        return distance == null ? Integer.MAX_VALUE : distance;
    }
}
