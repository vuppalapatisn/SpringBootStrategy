package com.example.payments.gateway.impl;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentStatus;
import com.example.payments.domain.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MockCardGatewayTest {

    private final MockCardGateway gateway = new MockCardGateway();

    @Test
    void charge_success_when_last_digit_even() {
        var req = new PaymentRequest(PaymentType.CARD, new BigDecimal("100.50"), "USD", Map.of("cardNumber", "4111111111111112"));
        var resp = gateway.charge(req);

        assertEquals(PaymentStatus.SUCCESS, resp.status());
        assertEquals("Card charged", resp.message());
        assertNotNull(resp.transactionId());
        assertEquals(new BigDecimal("100.50"), resp.finalAmount());
    }

    @Test
    void charge_failure_when_last_digit_odd() {
        var req = new PaymentRequest(PaymentType.CARD, new BigDecimal("50.00"), "USD", Map.of("cardNumber", "4111111111111111"));
        var resp = gateway.charge(req);

        assertEquals(PaymentStatus.FAILURE, resp.status());
        assertEquals("Card declined", resp.message());
    }

    @Test
    void charge_failure_when_no_cardNumber() {
        var req = new PaymentRequest(PaymentType.CARD, new BigDecimal("10.00"), "USD", Map.of());
        var resp = gateway.charge(req);

        assertEquals(PaymentStatus.FAILURE, resp.status());
    }
}
