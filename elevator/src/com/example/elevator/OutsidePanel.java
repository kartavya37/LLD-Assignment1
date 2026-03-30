package com.example.elevator;

public class OutsidePanel {
    private final int floor;
    private final int maxFloor;

    public OutsidePanel(int floor, int maxFloor) {
        this.floor = floor;
        this.maxFloor = maxFloor;
    }

    public OutsideRequest pressUp() {
        if (floor == maxFloor) {
            throw new IllegalStateException("Top floor does not support UP button.");
        }
        return new OutsideRequest(floor, Direction.UP);
    }

    public OutsideRequest pressDown() {
        if (floor == 0) {
            throw new IllegalStateException("Ground floor does not support DOWN button.");
        }
        return new OutsideRequest(floor, Direction.DOWN);
    }
}
