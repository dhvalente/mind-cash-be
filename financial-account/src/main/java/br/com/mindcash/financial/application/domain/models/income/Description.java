package br.com.mindcash.financial.application.domain.models.income;

public record Description(String value) {
    public Description {
        if (value == null || value.trim().length() < 10) {
            throw new IllegalArgumentException("A descrição da receita deve ter no mínimo 10 caracteres.");
        }
    }
}

