package com.example.payments.strategy;

import com.example.payments.domain.*;
import com.example.payments.gateway.PaymentGateway;
import com.example.payments.policy.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class UpiPaymentStrategy implements PaymentStrategy {
    private final FraudCheckPolicy fraud;
    private final PricingPolicy pricing;
    private final RetryPolicy retry;
    private final NotificationPolicy notifier;
    private final PaymentGateway gateway;

    public UpiPaymentStrategy(FraudCheckPolicy fraud,
                              PricingPolicy pricing,
                              RetryPolicy retry,
                              NotificationPolicy notifier,
                              @Qualifier("upiGateway") PaymentGateway gateway) {
        this.fraud = fraud;
        this.pricing = pricing;
        this.retry = retry;
        this.notifier = notifier;
        this.gateway = gateway;
    }

    @Override
    public PaymentType type() { return PaymentType.UPI; }

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        fraud.check(request);
        var priced = new PaymentRequest(request.type(), pricing.finalAmount(request), request.currency(), request.attributes());
        PaymentResponse response = retry.execute(() -> gateway.charge(priced));
        notifier.notifyResult(response);
        return new PaymentResponse(response.status(), response.message(), response.transactionId(), priced.amount());
    }
}
