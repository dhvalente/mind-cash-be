package br.com.mindcash.financial.application.ports.inbound;

import br.com.mindcash.financial.application.commands.AccountCommand;

public interface CommandHandler<C, R> {
    void handler(C command);
}

