package com.example.payments.policy;

import java.util.function.Supplier;

public interface RetryPolicy {
    <T> T execute(Supplier<T> supplier);
}
