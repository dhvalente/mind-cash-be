package br.com.mindcash.financial.application.domain.models.expense;

import java.util.UUID;

public record ExpenseId(UUID value) {
    public ExpenseId() {
        this(UUID.randomUUID());
    }
}

