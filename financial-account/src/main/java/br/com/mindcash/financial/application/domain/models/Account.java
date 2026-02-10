package br.com.mindcash.financial.application.domain.models;

import br.com.mindcash.financial.application.commands.RegisterExpense;
import br.com.mindcash.financial.application.commands.RegisterIncome;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.expense.ExpenseId;
import br.com.mindcash.financial.application.domain.models.expense.ExpenseInstant;
import br.com.mindcash.financial.application.domain.models.income.IncomeId;
import br.com.mindcash.financial.application.domain.models.income.IncomeInstant;

import java.util.UUID;

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
                new ExpenseInstant(java.time.Instant.now())
        );
    }

    public IncomeRegistered handle(RegisterIncome command) {
        return new IncomeRegistered(
                new IncomeId(UUID.randomUUID()),
                command.accountId(),
                command.description(),
                command.type(),
                command.category(),
                command.status(),
                command.amount(),
                new IncomeInstant(java.time.Instant.now())
        );
    }
}
