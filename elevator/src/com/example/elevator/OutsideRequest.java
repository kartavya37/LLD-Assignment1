package com.example.elevator;

public class OutsideRequest {
    private final int floor;
    private final Direction direction;

    public OutsideRequest(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public String key() {
        return floor + ":" + direction.name();
    }
}
