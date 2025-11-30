package com.example.payments.web;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentResponse;
import com.example.payments.domain.PaymentStatus;
import com.example.payments.domain.PaymentType;
import com.example.payments.service.PaymentOrchestrator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PaymentOrchestrator orchestrator;

    @Test
    void getPaymentTypes_returns_all_types() throws Exception {
        mvc.perform(get("/payments/types"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(PaymentType.values())));
    }

    @Test
    void getIndividualType_endpoints() throws Exception {
        mvc.perform(get("/payments/type/card")).andExpect(status().isOk()).andExpect(content().string("CARD"));
        mvc.perform(get("/payments/type/upi")).andExpect(status().isOk()).andExpect(content().string("UPI"));
        mvc.perform(get("/payments/type/wallet")).andExpect(status().isOk()).andExpect(content().string("WALLET"));
    }

    @Test
    void postPayment_delegates_to_orchestrator_and_returns_response() throws Exception {
        var req = new PaymentRequest(PaymentType.CARD, new BigDecimal("100.50"), "USD", Map.of("cardNumber", "4111111111111112"));
        var resp = new PaymentResponse(PaymentStatus.SUCCESS, "Card charged", "tx-123", req.amount());
        when(orchestrator.process(any(PaymentRequest.class))).thenReturn(resp);

        mvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(resp)));
    }

    @Test
    void postPayment_invalidPayload_returnsBadRequest() throws Exception {
        // missing amount -> validation should fail
        String body = "{\"type\":\"CARD\", \"currency\": \"USD\"}";

        mvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postPayment_invalidEnum_returnsBadRequest() throws Exception {
        String body = "{\"type\":\"UNKNOWN\", \"amount\":10.0, \"currency\": \"USD\"}";

        mvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("JSON parse error")));
    }

    @Test
    void postPayment_validationMessage_includesFieldName() throws Exception {
        String body = "{\"type\":\"CARD\", \"amount\":0, \"currency\": \"\"}"; // amount not positive, currency blank

        mvc.perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("amount")))
                .andExpect(content().string(containsString("must be greater than 0")));
    }
}
