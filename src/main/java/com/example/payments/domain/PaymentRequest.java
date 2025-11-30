package com.example.payments.domain;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Map;

public record PaymentRequest(
        @NotNull PaymentType type,
        @NotNull @Positive BigDecimal amount,
        @NotBlank String currency,
        Map<String, Object> attributes
) {}
