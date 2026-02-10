package br.com.mindcash.financial.application.domain.events;

import br.com.mindcash.financial.application.domain.models.income.IncomeId;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.income.Amount;
import br.com.mindcash.financial.application.domain.models.income.Description;
import br.com.mindcash.financial.application.domain.models.income.Category;
import br.com.mindcash.financial.application.domain.models.income.Status;
import br.com.mindcash.financial.application.domain.models.income.IncomeInstant;
import br.com.mindcash.financial.application.domain.models.income.Type;

public record IncomeRegistered(
    IncomeId incomeId,
    AccountId accountId,
    Description description,
    Type type,
    Category category,
    Status status,
    Amount amount,
    IncomeInstant incomeInstant
) implements AccountEvent {
}

