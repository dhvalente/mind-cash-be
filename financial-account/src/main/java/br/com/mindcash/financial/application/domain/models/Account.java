package br.com.mindcash.financial.application.domain.models;

import java.util.ArrayList;
import java.util.List;
import br.com.mindcash.financial.application.commands.RegisterExpense;
import br.com.mindcash.financial.application.commands.RegisterIncome;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.account.Country;
import br.com.mindcash.financial.application.domain.models.account.Expense;
import br.com.mindcash.financial.application.domain.models.account.Income;

public class Account {
    private final AccountId id;

    public Account(AccountId id) {
        this.id = id;
    }

    public AccountId getId() {
        return id;
    }

    public ExpenseRegistered handle(RegisterExpense command) {
        return new ExpenseRegistered(
                command.expenseId(),
                command.accountId(),
                command.description(),
                command.category(),
                command.status(),
                command.amount(),
                command.instant()
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
