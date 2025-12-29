package br.com.mindcash.financial.application.domain.models;

import java.util.UUID;
import br.com.mindcash.financial.application.commands.RegisterExpense;
import br.com.mindcash.financial.application.commands.RegisterIncome;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.expense.ExpenseId;
import br.com.mindcash.financial.application.domain.models.expense.Instant;

public record Account(AccountId id) {

    public ExpenseRegistered handle(RegisterExpense command) {
        return new ExpenseRegistered(
                new ExpenseId(UUID.randomUUID()),
                command.accountId(),
                command.description(),
                command.type(),
                command.category(),
                command.status(),
                command.amount(),
                new Instant(java.time.Instant.now())
        );
    }

    public IncomeRegistered handle(RegisterIncome command) {
        return new IncomeRegistered(
                command.incomeId(),
                command.accountId(),
                command.description(),
                command.category(),
                command.status(),
                command.amount(),
                command.instant()
        );
    }
}
