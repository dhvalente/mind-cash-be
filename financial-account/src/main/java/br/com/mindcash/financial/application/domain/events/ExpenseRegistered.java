package br.com.mindcash.financial.application.domain.events;

import br.com.mindcash.financial.application.domain.models.expense.ExpenseId;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.expense.Amount;
import br.com.mindcash.financial.application.domain.models.expense.Description;
import br.com.mindcash.financial.application.domain.models.expense.Category;
import br.com.mindcash.financial.application.domain.models.expense.Status;
import br.com.mindcash.financial.application.domain.models.expense.Instant;
import br.com.mindcash.financial.application.domain.models.expense.Type;

public record ExpenseRegistered(
    ExpenseId expenseId,
    AccountId accountId,
    Description description,
    Type type,
    Category category,
    Status status,
    Amount amount,
    Instant instant
) implements AccountEvent {
}

