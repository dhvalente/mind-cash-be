package br.com.mindcash.financial.driven.account.statements;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.driven.account.statements.expense.ExpenseRegisteredStatement;
import br.com.mindcash.financial.driven.account.statements.income.IncomeRegisteredStatement;
import br.com.mindcash.financial.driven.account.statements.outbox.OutboxStatement;
import br.com.mindcash.financial.driven.revision.v1.AggregateLoader;
import br.com.mindcash.financial.driven.revision.v1.aggregate.AggregateV1;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountEventStatements {

    private final AggregateLoader aggregateLoader;

    public AccountEventStatements(AggregateLoader aggregateLoader) {
        this.aggregateLoader = aggregateLoader;
    }

    public List<Statement> from(AccountEvent event) {
        List<Statement> statements = new ArrayList<>();

        // Statement do evento principal (insert na tabela específica)
        Statement eventStatement = switch (event) {
            case ExpenseRegistered ev -> ExpenseRegisteredStatement.from(ev);
            case IncomeRegistered ev -> IncomeRegisteredStatement.from(ev);
        };
        statements.add(eventStatement);

        // Carrega o agregado atual, aplica o evento e persiste na outbox
        AccountId accountId = extractAccountId(event);
        AggregateV1 currentAggregate = aggregateLoader.load(accountId);
        AggregateV1 newAggregate = currentAggregate.apply(event);

        Statement outboxStatement = OutboxStatement.from(event, newAggregate);
        statements.add(outboxStatement);

        return statements;
    }

    private AccountId extractAccountId(AccountEvent event) {
        return switch (event) {
            case ExpenseRegistered ev -> ev.accountId();
            case IncomeRegistered ev -> ev.accountId();
        };
    }
}