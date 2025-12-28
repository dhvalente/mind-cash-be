package br.com.mindcash.financial.application.domain.models.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Installment(
        int installmentNumber,
        BigDecimal installmentAmount,
        LocalDate dueDate
) {
}
