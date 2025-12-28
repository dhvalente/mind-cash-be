package br.com.mindcash.financial.application.handlers;

import br.com.mindcash.financial.application.commands.RegisterExpense;
import br.com.mindcash.financial.application.domain.models.Account;
import br.com.mindcash.financial.application.ports.inbound.CommandHandler;
import br.com.mindcash.financial.application.ports.outbound.Accounts;
import org.springframework.stereotype.Component;

@Component
public class RegisterExpenseHandler implements CommandHandler <RegisterExpense, Void>{

    private final Accounts accounts;

    public RegisterExpenseHandler(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public void handler(RegisterExpense command) {
        Account account = accounts.find(command.accountId());
        var event = account.handle(command);
        accounts.save(event);
    }
}
