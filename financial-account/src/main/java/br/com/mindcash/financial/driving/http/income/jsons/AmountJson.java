package br.com.mindcash.financial.driving.http.income.jsons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Currency;

@JsonSerialize
public record AmountJson(BigDecimal value, Currency currency) {
}
