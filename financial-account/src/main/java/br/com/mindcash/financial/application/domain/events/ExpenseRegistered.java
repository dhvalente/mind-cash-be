package br.com.mindcash.financial.application.domain.events;

import br.com.mindcash.financial.application.domain.models.expense.*;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.expense.ExpenseInstant;

public record ExpenseRegistered(
    ExpenseId expenseId,
    AccountId accountId,
    Description description,
    Type type,
    Category category,
    Status status,
    Amount amount,
    ExpenseInstant expenseInstant
) implements AccountEvent {
}

