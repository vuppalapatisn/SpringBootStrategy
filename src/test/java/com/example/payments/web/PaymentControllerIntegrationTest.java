package com.example.payments.web;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate rest;

    @Test
    void getTypes_and_postPayment_integration() {
        String base = "http://localhost:" + port + "/payments";

        ResponseEntity<String[]> types = rest.getForEntity(base + "/types", String[].class);
        assertThat(types.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(types.getBody()).contains("CARD", "UPI", "WALLET");

        var req = new PaymentRequest(PaymentType.CARD, new BigDecimal("10.00"), "USD", Map.of("cardNumber","4111111111111112"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(req, headers);

        ResponseEntity<String> resp = rest.postForEntity(base, entity, String.class);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(resp.getBody()).contains("transactionId");
    }
}
