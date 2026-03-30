package com.example.bookmyshow;

public class RefundReceipt {
    private final String ticketId;
    private final double amount;
    private final String refundTransactionId;

    public RefundReceipt(String ticketId, double amount, String refundTransactionId) {
        this.ticketId = ticketId;
        this.amount = amount;
        this.refundTransactionId = refundTransactionId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public String getRefundTransactionId() {
        return refundTransactionId;
    }
}
