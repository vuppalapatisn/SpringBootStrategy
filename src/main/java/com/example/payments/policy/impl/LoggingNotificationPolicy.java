package com.example.payments.policy.impl;

import com.example.payments.domain.PaymentResponse;
import com.example.payments.policy.NotificationPolicy;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingNotificationPolicy implements NotificationPolicy {
    private static final Logger log = LoggerFactory.getLogger(LoggingNotificationPolicy.class);
    @Override
    public void notifyResult(PaymentResponse response) {
        log.info("Payment result: {} - {} (tx={}) amount={}", 
                response.status(), response.message(), response.transactionId(), response.finalAmount());
    }
}
