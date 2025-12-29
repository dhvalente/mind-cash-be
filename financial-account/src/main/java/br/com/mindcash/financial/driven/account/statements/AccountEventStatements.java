package br.com.mindcash.financial.driven.account.statements;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.driven.account.statements.expense.ExpenseRegisteredStatement;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AccountEventStatements {

    public List<Statement> from(AccountEvent event) {
        return switch (event) {
            case ExpenseRegistered ev -> List.of(ExpenseRegisteredStatement.from(ev));
            case IncomeRegistered ev -> throw new IllegalStateException("Mapper não implementado para IncomeRegistered");
        };
    }
}