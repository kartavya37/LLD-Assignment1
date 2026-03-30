package com.example.elevator;

import java.util.List;

public interface ElevatorDispatchStrategy {
    ElevatorCar selectElevator(List<ElevatorCar> cars, OutsideRequest request);
}
