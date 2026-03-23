package src.com.example.parking_lots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Application {
    public static void main(String[] args) {
        Gate gate1 = new Gate(1, 0);
        Gate gate2 = new Gate(2, 0);
        List<Gate> gates = List.of(gate1, gate2);

        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot(0, SlotType.SMALL, buildDistanceMap(gate1, 2, gate2, 7)));
        slots.add(new Slot(0, SlotType.MEDIUM, buildDistanceMap(gate1, 4, gate2, 3)));
        slots.add(new Slot(0, SlotType.LARGE, buildDistanceMap(gate1, 8, gate2, 2)));

        SlotManager slotManager = new SlotManager(slots, new NearestSlotFromLevel());
        PriceStrategy priceStrategy = new HourlyPrice();
        Map<UUID, Ticket> tickets = new HashMap<>();

        ParkingLot parkingLot = new ParkingLot(1, slotManager, gates, tickets, priceStrategy);

        Vehicle bike = new Vehicle("BIKE-101", VehicleType.TWO_WHEELER);
        Vehicle car = new Vehicle("CAR-202", VehicleType.CARS);
        Vehicle bus = new Vehicle("BUS-303", VehicleType.BUSES);

        parkingLot.parkVehicle(bike, gate1);
        parkingLot.parkVehicle(car, gate1);
        parkingLot.parkVehicle(bus, gate1);

        System.out.println("Active tickets after parking: " + tickets.size());
        parkingLot.slotStatus(slots);

        UUID ticketIdToExit = tickets.keySet().stream().findFirst().orElse(null);
        if (ticketIdToExit != null) {
            double amount = parkingLot.exit(ticketIdToExit);
            System.out.println("Exit amount for ticket " + ticketIdToExit + ": " + amount);
        }

        System.out.println("Active tickets after one exit: " + tickets.size());
        parkingLot.slotStatus(slots);

        parkingLot.parkVehicle(new Vehicle("CAR-404", VehicleType.CARS), gate2);
        System.out.println("Final active tickets: " + tickets.size());
        parkingLot.slotStatus(slots);
    }

    private static Map<Gate, Integer> buildDistanceMap(Gate gate1, int distance1, Gate gate2, int distance2) {
        Map<Gate, Integer> distances = new HashMap<>();
        distances.put(gate1, distance1);
        distances.put(gate2, distance2);
        return distances;
    }
}
