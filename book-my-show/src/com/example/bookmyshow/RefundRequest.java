package com.example.bookmyshow;

public class RefundRequest {
    private final String paymentId;
    private final double amount;

    public RefundRequest(String paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }
}
