package com.example.bookmyshow;

public class PaymentRequest {
    private final String bookingRef;
    private final double amount;

    public PaymentRequest(String bookingRef, double amount) {
        this.bookingRef = bookingRef;
        this.amount = amount;
    }

    public String getBookingRef() {
        return bookingRef;
    }

    public double getAmount() {
        return amount;
    }
}
