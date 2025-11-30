package com.example.payments.service;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentResponse;
import com.example.payments.domain.PaymentType;
import com.example.payments.strategy.PaymentStrategy;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentOrchestrator {
    private final Map<PaymentType, PaymentStrategy> registry = new EnumMap<>(PaymentType.class);

    public PaymentOrchestrator(List<PaymentStrategy> strategies) {
        for (PaymentStrategy s : strategies) {
            registry.put(s.type(), s);
        }
    }

    public PaymentResponse process(PaymentRequest request) {
        PaymentStrategy strategy = registry.get(request.type());
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy registered for type: " + request.type());
        }
        return strategy.pay(request);
    }
}
