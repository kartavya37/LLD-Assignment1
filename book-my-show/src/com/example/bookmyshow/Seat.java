package com.example.bookmyshow;

public class Seat {
    private final String seatId;
    private final SeatType type;
    private final double basePrice;

    public Seat(String seatId, SeatType type, double basePrice) {
        this.seatId = seatId;
        this.type = type;
        this.basePrice = basePrice;
    }

    public String getSeatId() {
        return seatId;
    }

    public SeatType getType() {
        return type;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
