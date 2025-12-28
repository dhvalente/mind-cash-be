package br.com.mindcash.financial.application.domain.models.income;

import java.math.BigDecimal;
import java.util.Currency;

public record Amount(BigDecimal value, Currency currency) {
    public Amount {
        if (value == null || currency == null) {
            throw new IllegalArgumentException("Value and currency must not be null.");
        }
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Income amount must be positive.");
        }
        int maxScale = 10;
        if (value.scale() > maxScale) {
            throw new IllegalArgumentException("Amount cannot exceed " + maxScale + " decimal places.");
        }
    }
}
