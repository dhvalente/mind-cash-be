package br.com.mindcash.financial.driven.account.statements.expense;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import java.sql.Timestamp;

public final class ExpenseRegisteredStatement {

    private ExpenseRegisteredStatement() {}

    public static AccountEvent.Statement from(ExpenseRegistered event) {
        String sql = "INSERT INTO expense (" +
                "idt_expense, idt_account, dat_expense, des_expense, ind_category, ind_status, val_amount, cod_currency" +
                ") VALUES (" +
                "UUID_TO_BIN(?), UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?" +
                ")";

        Object[] args = new Object[] {
                String.valueOf(event.expenseId().value()),
                String.valueOf(event.accountId().value()),
                Timestamp.from(event.instant().value()),
                String.valueOf(event.description().value()),
                String.valueOf(event.category().toString()),
                String.valueOf(event.status().toString()),
                event.amount().value(),
                event.amount().currency().getCurrencyCode()
        };

        return new AccountEvent.Statement(sql, args);
    }
}
