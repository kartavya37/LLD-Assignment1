package com.example.elevator;

import java.util.List;

public class FcfSElevatorStrategy implements ElevatorDispatchStrategy {
    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> cars, OutsideRequest request) {
        for (ElevatorCar car : cars) {
            if (car.canAcceptRequests()) {
                return car;
            }
        }
        return null;
    }
}
