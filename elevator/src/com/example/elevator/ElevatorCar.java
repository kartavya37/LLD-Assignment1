package com.example.elevator;

import java.util.TreeSet;

public class ElevatorCar {
    private final int carId;
    private final int maxWeight;
    private int maxFloor;
    private final InsidePanel insidePanel;

    private int currentFloor;
    private int currentWeight;
    private ElevatorState state;
    private DoorState doorState;
    private boolean emergencyMode;

    private final TreeSet<Integer> upStops;
    private final TreeSet<Integer> downStops;

    public ElevatorCar(int carId, int maxWeight, int maxFloor) {
        this.carId = carId;
        this.maxWeight = maxWeight;
        this.maxFloor = maxFloor;
        this.currentFloor = 0;
        this.currentWeight = 0;
        this.state = ElevatorState.IDLE;
        this.doorState = DoorState.CLOSED;
        this.emergencyMode = false;
        this.upStops = new TreeSet<>();
        this.downStops = new TreeSet<>();
        this.insidePanel = new InsidePanel(this);
    }

    public int getCarId() {
        return carId;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public InsidePanel getInsidePanel() {
        return insidePanel;
    }

    public boolean canAcceptRequests() {
        return state != ElevatorState.UNDER_MAINTENANCE && !emergencyMode;
    }

    public void setMaintenance(boolean enabled) {
        if (enabled) {
            state = ElevatorState.UNDER_MAINTENANCE;
            upStops.clear();
            downStops.clear();
            openDoor();
            return;
        }
        if (state == ElevatorState.UNDER_MAINTENANCE) {
            state = ElevatorState.IDLE;
            closeDoor();
        }
    }

    public void extendMaxFloor(int newMaxFloor) {
        if (newMaxFloor > this.maxFloor) {
            this.maxFloor = newMaxFloor;
        }
    }

    public void addLoad(int weight) {
        currentWeight += weight;
        if (currentWeight > maxWeight) {
            openDoor();
            playOverWeightSong();
        }
    }

    public void removeLoad(int weight) {
        currentWeight = Math.max(0, currentWeight - weight);
    }

    public void addStop(int floor) {
        validateFloor(floor);
        if (!canAcceptRequests()) {
            return;
        }

        if (floor >= currentFloor) {
            upStops.add(floor);
            if (state == ElevatorState.IDLE) {
                state = ElevatorState.UP;
            }
        } else {
            downStops.add(floor);
            if (state == ElevatorState.IDLE) {
                state = ElevatorState.DOWN;
            }
        }
    }

    public void step() {
        if (!canAcceptRequests()) {
            return;
        }

        if (currentWeight > maxWeight) {
            openDoor();
            playOverWeightSong();
            return;
        }

        if (state == ElevatorState.IDLE) {
            return;
        }

        if (state == ElevatorState.UP) {
            Integer next = getNextUpStop();
            if (next == null) {
                if (!downStops.isEmpty()) {
                    state = ElevatorState.DOWN;
                    step();
                } else {
                    state = ElevatorState.IDLE;
                }
                return;
            }

            moveToward(next);
            if (currentFloor == next) {
                upStops.remove(next);
                openDoor();
                closeDoor();
                if (upStops.isEmpty() && downStops.isEmpty()) {
                    state = ElevatorState.IDLE;
                }
            }
            return;
        }

        if (state == ElevatorState.DOWN) {
            Integer next = getNextDownStop();
            if (next == null) {
                if (!upStops.isEmpty()) {
                    state = ElevatorState.UP;
                    step();
                } else {
                    state = ElevatorState.IDLE;
                }
                return;
            }

            moveToward(next);
            if (currentFloor == next) {
                downStops.remove(next);
                openDoor();
                closeDoor();
                if (upStops.isEmpty() && downStops.isEmpty()) {
                    state = ElevatorState.IDLE;
                }
            }
        }
    }

    public void ringAlarm() {
        System.out.println("Car-" + carId + " alarm ringing.");
    }

    public void activateEmergency() {
        emergencyMode = true;
        upStops.clear();
        downStops.clear();
        openDoor();
        ringAlarm();
    }

    public void resetEmergency() {
        emergencyMode = false;
        if (state != ElevatorState.UNDER_MAINTENANCE) {
            state = ElevatorState.IDLE;
            closeDoor();
        }
    }

    public void openDoor() {
        doorState = DoorState.OPEN;
    }

    public void closeDoor() {
        if (currentWeight <= maxWeight) {
            doorState = DoorState.CLOSED;
        }
    }

    private void moveToward(int targetFloor) {
        if (targetFloor > currentFloor) {
            currentFloor++;
            state = ElevatorState.UP;
        } else if (targetFloor < currentFloor) {
            currentFloor--;
            state = ElevatorState.DOWN;
        }
    }

    private Integer getNextUpStop() {
        Integer candidate = upStops.ceiling(currentFloor);
        if (candidate != null) {
            return candidate;
        }
        return upStops.isEmpty() ? null : upStops.first();
    }

    private Integer getNextDownStop() {
        Integer candidate = downStops.floor(currentFloor);
        if (candidate != null) {
            return candidate;
        }
        return downStops.isEmpty() ? null : downStops.last();
    }

    private void playOverWeightSong() {
        System.out.println("Car-" + carId + " overweight: doors stay open, warning sound playing.");
    }

    private void validateFloor(int floor) {
        if (floor < 0 || floor > maxFloor) {
            throw new IllegalArgumentException("Invalid floor: " + floor);
        }
    }
}
