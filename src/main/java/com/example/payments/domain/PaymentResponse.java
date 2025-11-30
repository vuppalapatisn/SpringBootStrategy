package com.example.payments.domain;

import java.math.BigDecimal;

public record PaymentResponse(
        PaymentStatus status,
        String message,
        String transactionId,
        BigDecimal finalAmount
) {}
