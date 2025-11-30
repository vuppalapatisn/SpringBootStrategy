package com.example.payments.gateway.impl;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentResponse;
import com.example.payments.domain.PaymentStatus;
import com.example.payments.gateway.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("upiGateway")
public class MockUpiGateway implements PaymentGateway {
    @Override
    public PaymentResponse charge(PaymentRequest request) {
        String upi = String.valueOf(request.attributes().getOrDefault("upiId", ""));
        boolean success = upi.contains("@");
        return new PaymentResponse(
                success ? PaymentStatus.SUCCESS : PaymentStatus.FAILURE,
                success ? "UPI charged" : "UPI failed",
                UUID.randomUUID().toString(),
                request.amount()
        );
    }
}
