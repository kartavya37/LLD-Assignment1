package com.example.bookmyshow;

import java.util.UUID;

public class MockPaymentGateway implements PaymentGateway {
    @Override
    public PaymentResult pay(PaymentRequest request) {
        String paymentId = "PAY-" + UUID.randomUUID().toString();
        return new PaymentResult(true, paymentId, "Payment successful");
    }

    @Override
    public PaymentResult refund(RefundRequest request) {
        String refundId = "RFND-" + UUID.randomUUID().toString();
        return new PaymentResult(true, refundId, "Refund successful");
    }
}
