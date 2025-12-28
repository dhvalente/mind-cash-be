package br.com.mindcash.financial.application.ports.outbound;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.models.Account;
import br.com.mindcash.financial.application.domain.models.account.AccountId;

public interface Accounts {

    AccountEvent save(AccountEvent event);

    Account find(AccountId id);
}
