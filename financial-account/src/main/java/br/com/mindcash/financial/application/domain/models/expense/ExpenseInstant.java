package br.com.mindcash.financial.application.domain.models.expense;

public record ExpenseInstant(java.time.Instant value) {
    public ExpenseInstant {
        if (value == null) {
            throw new IllegalArgumentException("Instant value must not be null.");
        }
        if (value.isAfter(java.time.Instant.now())) {
            throw new IllegalArgumentException("Instant value must not be in the future.");
        }
    }
}

