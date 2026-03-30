package com.example.bookmyshow;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Ticket {
    private final String ticketId;
    private final String showId;
    private final Set<String> seatIds;
    private final double totalAmount;
    private final String paymentId;
    private TicketStatus status;

    public Ticket(String ticketId, String showId, Set<String> seatIds, double totalAmount, String paymentId) {
        this.ticketId = ticketId;
        this.showId = showId;
        this.seatIds = new HashSet<>(seatIds);
        this.totalAmount = totalAmount;
        this.paymentId = paymentId;
        this.status = TicketStatus.CONFIRMED;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getShowId() {
        return showId;
    }

    public Set<String> getSeatIds() {
        return Collections.unmodifiableSet(seatIds);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void markCancelled() {
        this.status = TicketStatus.CANCELLED;
    }
}
