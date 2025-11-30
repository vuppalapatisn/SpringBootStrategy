package com.example.payments.policy.impl;

import com.example.payments.policy.RetryPolicy;
import org.springframework.stereotype.Component;

@Component
public class NoRetryPolicy implements RetryPolicy {
    @Override
    public <T> T execute(java.util.function.Supplier<T> supplier) {
        return supplier.get();
    }
}
