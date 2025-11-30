package com.example.payments.web;

import com.example.payments.domain.PaymentRequest;
import com.example.payments.domain.PaymentResponse;
import com.example.payments.domain.PaymentType; // Import PaymentType enum
import com.example.payments.service.PaymentOrchestrator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentOrchestrator orchestrator;

    public PaymentController(PaymentOrchestrator orchestrator) { this.orchestrator = orchestrator; }

    @PostMapping
    public ResponseEntity<PaymentResponse> pay(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(orchestrator.process(request));
    }

    // REST API to get all PaymentTypes
    @GetMapping("/types")
    public ResponseEntity<PaymentType[]> getPaymentTypes() {
        return ResponseEntity.ok(PaymentType.values());
    }

    @GetMapping("/type/card")
    public ResponseEntity<String> getCardType() {
        return ResponseEntity.ok(PaymentType.CARD.name());
    }

    @GetMapping("/type/upi")
    public ResponseEntity<String> getUpiType() {
        return ResponseEntity.ok(PaymentType.UPI.name());
    }

    @GetMapping("/type/wallet")
    public ResponseEntity<String> getWalletType() {
        return ResponseEntity.ok(PaymentType.WALLET.name());
    }

}
