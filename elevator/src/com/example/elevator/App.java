package com.example.elevator;

public class App {
    public static void main(String[] args) {
        ElevatorSystem system = new ElevatorSystem(10, 3, 600, DispatchMode.SHORTEST_SEEK);

        System.out.println("=== Initial Status ===");
        system.printStatus();

        System.out.println("=== Outside Requests ===");
        system.requestFromFloor(0, Direction.UP);
        system.requestFromFloor(6, Direction.DOWN);
        system.requestFromFloor(4, Direction.UP);

        for (int i = 0; i < 6; i++) {
            system.stepAll();
            system.printStatus();
        }

        System.out.println("=== Inside Panel Usage ===");
        system.pressInsideFloorButton(1, 9);
        system.stepAll();
        system.printStatus();

        System.out.println("=== Weight Limit Exceeded ===");
        system.addPassengerWeight(1, 700);
        system.stepAll();
        system.printStatus();
        system.removePassengerWeight(1, 300);

        System.out.println("=== Maintenance Mode ===");
        system.setMaintenance(2, true);
        system.stepAll();
        system.printStatus();
        system.setMaintenance(2, false);

        System.out.println("=== Emergency Broadcast ===");
        system.emergencyAlarmAll();
        system.printStatus();
        system.resetEmergencyAll();

        System.out.println("=== Add More Floors ===");
        system.addFloors(2);
        system.requestFromFloor(11, Direction.DOWN);
        for (int i = 0; i < 5; i++) {
            system.stepAll();
            system.printStatus();
        }
    }
}
