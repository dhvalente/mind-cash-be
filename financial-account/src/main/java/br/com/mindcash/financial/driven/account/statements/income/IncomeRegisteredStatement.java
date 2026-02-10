package br.com.mindcash.financial.driven.account.statements.income;

import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.driven.account.statements.Statement;

import java.sql.Timestamp;

public final class IncomeRegisteredStatement {

    private IncomeRegisteredStatement() {}

    public static Statement from(IncomeRegistered event) {
        String sql = "INSERT INTO income (" +
                "idt_income, idt_account, dat_income, des_income, ind_type, ind_category, ind_status, val_amount, cod_currency" +
                ") VALUES (" +
                "UUID_TO_BIN(?), UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?, ?" +
                ")";

        Object[] args = new Object[] {
                String.valueOf(event.incomeId().value()),
                String.valueOf(event.accountId().value()),
                Timestamp.from(event.incomeInstant().value()),
                String.valueOf(event.description().value()),
                String.valueOf(event.type().toString()),
                String.valueOf(event.category().toString()),
                String.valueOf(event.status().toString()),
                event.amount().value(),
                event.amount().currency().getCurrencyCode()
        };

        return new Statement(sql, args);
    }
}
