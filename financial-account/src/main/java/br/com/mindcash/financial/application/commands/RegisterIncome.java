package br.com.mindcash.financial.application.commands;

import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.income.*;

public record RegisterIncome(
        AccountId accountId,
        Description description,
        Type type,
        Category category,
        Status status,
        Amount amount
) implements AccountCommand {
}
