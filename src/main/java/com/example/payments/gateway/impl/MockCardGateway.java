package com.example.payments.gateway.impl;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentResponse;
import com.example.payments.domain.PaymentStatus;
import com.example.payments.gateway.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("cardGateway")
public class MockCardGateway implements PaymentGateway {
    @Override
    public PaymentResponse charge(PaymentRequest request) {
        String card = String.valueOf(request.attributes().getOrDefault("cardNumber", ""));
        boolean success = !card.isEmpty() && Character.isDigit(card.charAt(card.length()-1)) && (card.charAt(card.length()-1) - '0') % 2 == 0;
        return new PaymentResponse(
                success ? PaymentStatus.SUCCESS : PaymentStatus.FAILURE,
                success ? "Card charged" : "Card declined",
                UUID.randomUUID().toString(),
                request.amount()
        );
    }
}
