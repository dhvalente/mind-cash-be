package br.com.mindcash.financial.application.handlers;

import br.com.mindcash.financial.application.commands.RegisterIncome;
import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.models.Account;
import br.com.mindcash.financial.application.ports.inbound.CommandHandler;
import br.com.mindcash.financial.application.ports.outbound.Accounts;
import org.springframework.stereotype.Component;

@Component
public class RegisterIncomeHandler implements CommandHandler<RegisterIncome> {

    private final Accounts accounts;

    public RegisterIncomeHandler(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public void handler(RegisterIncome command) {
        Account account = accounts.find(command.accountId());
        if (account == null) {
            throw new IllegalStateException("Conta não encontrada: " + command.accountId());
        }
        AccountEvent event = account.handle(command);
        accounts.save(event);
    }
}