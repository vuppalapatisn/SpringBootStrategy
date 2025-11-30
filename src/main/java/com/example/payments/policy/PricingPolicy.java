package com.example.payments.policy;

import com.example.payments.domain.PaymentRequest;
import java.math.BigDecimal;

public interface PricingPolicy {
    BigDecimal finalAmount(PaymentRequest request);
}
