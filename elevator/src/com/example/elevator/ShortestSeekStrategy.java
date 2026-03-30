package com.example.elevator;

import java.util.List;

public class ShortestSeekStrategy implements ElevatorDispatchStrategy {
    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> cars, OutsideRequest request) {
        ElevatorCar best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (ElevatorCar car : cars) {
            if (!car.canAcceptRequests()) {
                continue;
            }

            int distance = Math.abs(car.getCurrentFloor() - request.getFloor());
            if (isDirectionCompatible(car, request) && distance < bestDistance) {
                bestDistance = distance;
                best = car;
            }
        }

        if (best != null) {
            return best;
        }

        for (ElevatorCar car : cars) {
            if (!car.canAcceptRequests()) {
                continue;
            }
            int distance = Math.abs(car.getCurrentFloor() - request.getFloor());
            if (distance < bestDistance) {
                bestDistance = distance;
                best = car;
            }
        }

        return best;
    }

    private boolean isDirectionCompatible(ElevatorCar car, OutsideRequest request) {
        if (car.getState() == ElevatorState.IDLE) {
            return true;
        }

        if (car.getState() == ElevatorState.UP && request.getDirection() == Direction.UP) {
            return request.getFloor() >= car.getCurrentFloor();
        }

        if (car.getState() == ElevatorState.DOWN && request.getDirection() == Direction.DOWN) {
            return request.getFloor() <= car.getCurrentFloor();
        }

        return false;
    }
}
