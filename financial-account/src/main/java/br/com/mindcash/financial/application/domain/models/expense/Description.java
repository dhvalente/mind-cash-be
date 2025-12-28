package br.com.mindcash.financial.application.domain.models.expense;

public record Description(String value) {
    public Description {
        if (value == null || value.trim().length() < 10) {
            throw new IllegalArgumentException("A descrição da despesa deve ter no mínimo 10 caracteres.");
        }
    }
}

