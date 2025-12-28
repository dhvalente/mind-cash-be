package br.com.mindcash.financial.driven.account;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.models.Account;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.ports.outbound.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static br.com.mindcash.financial.driven.account.Selects.FIND_BY_ID;

@Component
public class Adapter implements Accounts {
    private final JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    public Adapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AccountEvent save(AccountEvent event) {
        List<AccountEvent.Statement> stmts = event.statements();

        for (AccountEvent.Statement st : stmts) {
            String template = st.template();
            Object[] args = st.args();
            int updated;
            try {
                    updated = jdbcTemplate.update(template, args);
            } catch (DataAccessException ex) {
                throw new IllegalStateException("Failed to execute statement: " + template, ex);
            }
            if (updated <= 0) {
                throw new IllegalStateException("No rows affected for statement: " + template);
            }
        }
        return event;
    }

    @Override
    public Account find(AccountId id) {
        return jdbcTemplate.queryForObject(
                FIND_BY_ID,
                new Object[]{ id.value() },
                new int[]{ java.sql.Types.VARCHAR },
                (rs, rowNum) -> new Account(new AccountId(rs.getString("idt_account")))
        );
    }
}
