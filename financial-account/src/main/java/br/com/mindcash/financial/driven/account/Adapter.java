package br.com.mindcash.financial.driven.account;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.application.domain.models.Account;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.ports.outbound.Accounts;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Component
public class Adapter implements Accounts {
    private final DatabaseClient databaseClient;

    @Autowired(required = false)
    public Adapter(@Nullable DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }



    @Override
    public void save(AccountEvent event) {
        if (event instanceof ExpenseRegistered er) {
            var amount = er.amount();
            UUID transactionId = er.expenseId().value();
            LocalDateTime dat = LocalDateTime.ofInstant(er.instant().value(), ZoneOffset.UTC);

            databaseClient.sql("INSERT INTO transactions (idt_transaction, idt_transaction_type, dat_transaction, idt_account, val_amount, des_transaction, ind_category, ind_status, cod_currency) VALUES (:idt_transaction, :idt_transaction_type, :dat_transaction, :idt_account, :val_amount, :des_transaction, :ind_category, :ind_status, :cod_currency)")
                    .bind("idt_transaction", transactionId)
                    .bind("idt_transaction_type", "DEBIT")
                    .bind("dat_transaction", dat)
                    .bind("idt_account", UUID.fromString(er.accountId().value()))
                    .bind("val_amount", amount.value())
                    .bind("des_transaction", er.description().value())
                    .bind("ind_category", er.category().name())
                    .bind("ind_status", er.status().name())
                    .bind("cod_currency", amount.currency().getCurrencyCode())
                    .fetch()
                    .rowsUpdated()
                    .block();

            return;
        }

        if (event instanceof IncomeRegistered ir) {
            var amount = ir.amount();
            UUID transactionId = ir.incomeId().value();
            LocalDateTime dat = LocalDateTime.ofInstant(ir.instant().value(), ZoneOffset.UTC);

            databaseClient.sql("INSERT INTO transactions (idt_transaction, idt_transaction_type, dat_transaction, idt_account, val_amount, des_transaction, ind_category, ind_status, cod_currency) VALUES (:idt_transaction, :idt_transaction_type, :dat_transaction, :idt_account, :val_amount, :des_transaction, :ind_category, :ind_status, :cod_currency)")
                    .bind("idt_transaction", transactionId)
                    .bind("idt_transaction_type", "CREDIT")
                    .bind("dat_transaction", dat)
                    .bind("idt_account", UUID.fromString(ir.accountId().value()))
                    .bind("val_amount", amount.value())
                    .bind("des_transaction", ir.description().value())
                    .bind("ind_category", ir.category().name())
                    .bind("ind_status", ir.status().name())
                    .bind("cod_currency", amount.currency().getCurrencyCode())
                    .fetch()
                    .rowsUpdated()
                    .block();

            return;
        }

        throw new IllegalArgumentException("Unsupported AccountEvent type: " + event.getClass());
    }

    @Override
    public Account find(AccountId id) {
        return databaseClient.sql(Selects.FIND_BY_ID)
                .bind("id", UUID.fromString(id.value()))
                .map((row, metadata) -> {
                    java.util.UUID uuid = row.get("idt_account", java.util.UUID.class);
                    return new Account(new AccountId(uuid.toString()));
                })
                .one()
                .block();
    }
}
