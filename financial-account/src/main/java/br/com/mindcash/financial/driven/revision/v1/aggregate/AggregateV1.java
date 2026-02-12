package br.com.mindcash.financial.driven.revision.v1.aggregate;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import java.math.BigDecimal;
import java.util.Currency;

public record AggregateV1(
    AccountId accountId,
    BigDecimal balance,
    Currency currency,
    int revision
) {

    private static final String AGGREGATE_NAME = "Account";

    public static AggregateV1 empty(AccountId accountId) {
        return new AggregateV1(accountId, BigDecimal.ZERO, Currency.getInstance("BRL"), 0);
    }

    public AggregateV1 apply(AccountEvent event) {
        return switch (event) {
            case ExpenseRegistered ev -> applyExpense(ev);
            case IncomeRegistered ev -> applyIncome(ev);
        };
    }

    private AggregateV1 applyExpense(ExpenseRegistered event) {
        BigDecimal newBalance = balance.subtract(event.amount().value());
        Currency eventCurrency = event.amount().currency();
        return new AggregateV1(accountId, newBalance, eventCurrency, revision + 1);
    }

    private AggregateV1 applyIncome(IncomeRegistered event) {
        BigDecimal newBalance = balance.add(event.amount().value());
        Currency eventCurrency = event.amount().currency();
        return new AggregateV1(accountId, newBalance, eventCurrency, revision + 1);
    }

    public String aggregateName() {
        return AGGREGATE_NAME;
    }

    public String aggregateCode() {
        return accountId.value();
    }
}

