package com.example.payments.gateway;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentResponse;

public interface PaymentGateway {
    PaymentResponse charge(PaymentRequest request);
}
