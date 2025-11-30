package com.example.payments.policy.impl;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.policy.FraudCheckPolicy;
import org.springframework.stereotype.Component;

@Component
public class BasicFraudCheckPolicy implements FraudCheckPolicy {
    @Override
    public void check(PaymentRequest request) {
        if (request.amount().longValue() > 1_000_000L) {
            throw new IllegalArgumentException("Fraud check failed: amount too large");
        }
    }
}
