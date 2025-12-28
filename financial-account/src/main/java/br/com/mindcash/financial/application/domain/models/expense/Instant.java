package br.com.mindcash.financial.application.domain.models.expense;

public record Instant(java.time.Instant value) {
    public Instant {
        if (value == null) {
            throw new IllegalArgumentException("Instant value must not be null.");
        }
        if (value.isAfter(java.time.Instant.now())) {
            throw new IllegalArgumentException("Instant value must not be in the future.");
        }
        // Verifica precisão: só segundos, sem nanos
        if (value.getNano() != 0) {
            throw new IllegalArgumentException("Instant value must have second precision only (no nanoseconds).");
        }
    }
}

