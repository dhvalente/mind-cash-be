package br.com.mindcash.financial.application.ports.inbound;

public interface CommandHandler<C> {
    void handler(C command);
}

