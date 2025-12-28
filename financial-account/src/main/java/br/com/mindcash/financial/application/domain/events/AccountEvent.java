package br.com.mindcash.financial.application.domain.events;

public sealed interface AccountEvent permits ExpenseRegistered, IncomeRegistered {
}
