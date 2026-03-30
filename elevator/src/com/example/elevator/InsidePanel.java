package com.example.elevator;

public class InsidePanel {
    private final ElevatorCar car;

    public InsidePanel(ElevatorCar car) {
        this.car = car;
    }

    public void pressFloor(int floor) {
        car.addStop(floor);
    }

    public void pressOpenDoor() {
        car.openDoor();
    }

    public void pressCloseDoor() {
        car.closeDoor();
    }

    public void pressAlarm() {
        car.ringAlarm();
    }
}
