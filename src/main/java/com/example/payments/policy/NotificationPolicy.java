package com.example.payments.policy;

import com.example.payments.domain.PaymentResponse;

public interface NotificationPolicy {
    void notifyResult(PaymentResponse response);
}
