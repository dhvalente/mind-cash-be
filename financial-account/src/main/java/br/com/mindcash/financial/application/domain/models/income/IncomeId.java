package br.com.mindcash.financial.application.domain.models.income;

import java.util.UUID;

public record IncomeId(UUID value) {
    public IncomeId() {
        this(UUID.randomUUID());
    }
}

