package com.example.payments.policy;

import com.example.payments.domain.PaymentRequest;

public interface FraudCheckPolicy {
    void check(PaymentRequest request);
}
