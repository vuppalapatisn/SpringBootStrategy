package com.example.payments.policy.impl;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentType;
import com.example.payments.policy.PricingPolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DefaultPricingPolicy implements PricingPolicy {
    @Override
    public BigDecimal finalAmount(PaymentRequest request) {
        BigDecimal feeRate;
        if (request.type() == PaymentType.CARD) feeRate = new BigDecimal("0.018");
        else if (request.type() == PaymentType.UPI) feeRate = BigDecimal.ZERO;
        else feeRate = new BigDecimal("0.010");
        BigDecimal fee = request.amount().multiply(feeRate);
        return request.amount().add(fee).setScale(2, RoundingMode.HALF_UP);
    }
}
