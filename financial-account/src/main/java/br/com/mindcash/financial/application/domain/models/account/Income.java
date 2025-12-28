package br.com.mindcash.financial.application.domain.models.account;

import br.com.mindcash.financial.application.domain.models.income.IncomeId;
import br.com.mindcash.financial.application.domain.models.income.Description;
import br.com.mindcash.financial.application.domain.models.income.Category;
import br.com.mindcash.financial.application.domain.models.income.Status;
import br.com.mindcash.financial.application.domain.models.income.Amount;
import br.com.mindcash.financial.application.domain.models.income.Instant;

public record Income(
    IncomeId incomeId,
    AccountId accountId,
    Description description,
    Category category,
    Status status,
    Amount amount,
    Instant instant
) {}

