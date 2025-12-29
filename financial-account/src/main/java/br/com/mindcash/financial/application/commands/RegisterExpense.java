package br.com.mindcash.financial.application.commands;

import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.expense.*;
import java.util.List;

public record RegisterExpense(
        AccountId accountId,
        Description description,
        Type type,
        Category category,
        Status status,
        Amount amount,
        List<Installment> installments
) implements AccountCommand{
}
