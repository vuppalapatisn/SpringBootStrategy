package com.example.payments.strategy;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentResponse;
import com.example.payments.domain.PaymentType;

public sealed interface PaymentStrategy permits CardPaymentStrategy, UpiPaymentStrategy, ExtensiblePaymentStrategy {
    PaymentType type();
    PaymentResponse pay(PaymentRequest request);
}
