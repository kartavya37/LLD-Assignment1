package com.example.elevator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElevatorSystem {
    private final List<ElevatorCar> cars;
    private final Map<Integer, OutsidePanel> outsidePanels;
    private final Map<String, Integer> activeFloorAssignments;
    private ElevatorDispatchStrategy strategy;
    private int maxFloor;

    public ElevatorSystem(int maxFloor, int elevatorCount, int carWeightLimit, DispatchMode mode) {
        this.maxFloor = maxFloor;
        this.cars = new ArrayList<>();
        this.outsidePanels = new HashMap<>();
        this.activeFloorAssignments = new HashMap<>();
        this.strategy = strategyFromMode(mode);

        for (int i = 0; i <= maxFloor; i++) {
            outsidePanels.put(i, new OutsidePanel(i, maxFloor));
        }

        for (int i = 1; i <= elevatorCount; i++) {
            cars.add(new ElevatorCar(i, carWeightLimit, maxFloor));
        }
    }

    public List<ElevatorCar> getCars() {
        return cars;
    }

    public void setDispatchMode(DispatchMode mode) {
        this.strategy = strategyFromMode(mode);
    }

    public void addFloors(int additionalFloors) {
        if (additionalFloors <= 0) {
            return;
        }

        int oldMax = maxFloor;
        maxFloor += additionalFloors;
        for (int f = oldMax + 1; f <= maxFloor; f++) {
            outsidePanels.put(f, new OutsidePanel(f, maxFloor));
        }
        for (ElevatorCar car : cars) {
            car.extendMaxFloor(maxFloor);
        }
    }

    public void requestFromFloor(int floor, Direction direction) {
        OutsidePanel panel = outsidePanels.get(floor);
        if (panel == null) {
            throw new IllegalArgumentException("Floor not configured: " + floor);
        }

        OutsideRequest request;
        switch (direction) {
            case UP -> request = panel.pressUp();
            case DOWN -> request = panel.pressDown();
            default -> throw new IllegalArgumentException("Outside request requires UP or DOWN direction.");
        }

        assignRequest(request);
    }

    public void emergencyAlarmAll() {
        for (ElevatorCar car : cars) {
            car.activateEmergency();
        }
        activeFloorAssignments.clear();
    }

    public void resetEmergencyAll() {
        for (ElevatorCar car : cars) {
            car.resetEmergency();
        }
    }

    public void setMaintenance(int carId, boolean enabled) {
        ElevatorCar car = requireCar(carId);
        car.setMaintenance(enabled);
    }

    public void addPassengerWeight(int carId, int weight) {
        ElevatorCar car = requireCar(carId);
        car.addLoad(weight);
    }

    public void removePassengerWeight(int carId, int weight) {
        ElevatorCar car = requireCar(carId);
        car.removeLoad(weight);
    }

    public void pressInsideFloorButton(int carId, int floor) {
        ElevatorCar car = requireCar(carId);
        car.getInsidePanel().pressFloor(floor);
    }

    public void stepAll() {
        for (ElevatorCar car : cars) {
            car.step();
        }
        cleanupAssignments();
    }

    public void printStatus() {
        for (ElevatorCar car : cars) {
            System.out.println("Car-" + car.getCarId()
                    + " floor=" + car.getCurrentFloor()
                    + " state=" + car.getState()
                    + " door=" + car.getDoorState());
        }
    }

    private void assignRequest(OutsideRequest request) {
        String key = request.key();
        if (activeFloorAssignments.containsKey(key)) {
            return;
        }

        ElevatorCar selected = strategy.selectElevator(cars, request);
        if (selected == null) {
            throw new IllegalStateException("No available elevator for request at floor " + request.getFloor());
        }

        selected.addStop(request.getFloor());
        activeFloorAssignments.put(key, selected.getCarId());
    }

    private void cleanupAssignments() {
        List<String> toRemove = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : activeFloorAssignments.entrySet()) {
            String key = entry.getKey();
            int floor = Integer.parseInt(key.split(":")[0]);
            ElevatorCar car = requireCar(entry.getValue());
            if (car.getCurrentFloor() == floor && car.getDoorState() == DoorState.OPEN) {
                toRemove.add(key);
            }
        }

        for (String key : toRemove) {
            activeFloorAssignments.remove(key);
        }
    }

    private ElevatorCar requireCar(int carId) {
        for (ElevatorCar car : cars) {
            if (car.getCarId() == carId) {
                return car;
            }
        }
        throw new IllegalArgumentException("Elevator car not found: " + carId);
    }

    private ElevatorDispatchStrategy strategyFromMode(DispatchMode mode) {
        if (mode == DispatchMode.FCFS) {
            return new FcfSElevatorStrategy();
        }
        return new ShortestSeekStrategy();
    }
}
