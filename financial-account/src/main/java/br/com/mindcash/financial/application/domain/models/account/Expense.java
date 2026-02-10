package br.com.mindcash.financial.application.domain.models.account;

import br.com.mindcash.financial.application.domain.models.expense.*;
import br.com.mindcash.financial.application.domain.models.expense.ExpenseInstant;

public record Expense(
    ExpenseId expenseId,
    AccountId accountId,
    Description description,
    Type type,
    Category category,
    Status status,
    Amount amount,
    ExpenseInstant expenseInstant
) {}

