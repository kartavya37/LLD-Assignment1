package com.example.bookmyshow;

public interface PaymentGateway {
    PaymentResult pay(PaymentRequest request);

    PaymentResult refund(RefundRequest request);
}
