package br.com.mindcash.financial.driving.http.income.jsons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonSerialize
public record InstallmentJson(
        int installmentNumber,
        BigDecimal installmentAmount,
        LocalDate dueDate
) {
}