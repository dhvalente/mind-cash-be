package br.com.mindcash.financial.application.domain.models.income;

public record IncomeInstant(java.time.Instant value) {
    public IncomeInstant {
        if (value == null) {
            throw new IllegalArgumentException("Instant value must not be null.");
        }
        if (value.isAfter(java.time.Instant.now())) {
            throw new IllegalArgumentException("Instant value must not be in the future.");
        }
    }
}

