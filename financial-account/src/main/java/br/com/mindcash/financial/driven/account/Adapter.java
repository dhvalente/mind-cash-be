package br.com.mindcash.financial.driven.account;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.models.Account;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.ports.outbound.Accounts;
import br.com.mindcash.financial.driven.account.statements.AccountEventStatements;
import br.com.mindcash.financial.driven.account.statements.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.mindcash.financial.driven.account.Selects.FIND_BY_ID;

@Component
public class Adapter implements Accounts {
    private final JdbcTemplate jdbcTemplate;
    private final AccountEventStatements statementsMapper;

    public Adapter(JdbcTemplate jdbcTemplate, AccountEventStatements statementsMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.statementsMapper = statementsMapper;
    }

    @Override
    @Transactional
    public AccountEvent save(AccountEvent event) {
        List<Statement> stmts = statementsMapper.from(event);

        for (Statement st : stmts) {
            String template = st.template();
            Object[] args = st.args();
            int updated;
            try {
                updated = jdbcTemplate.update(template, args);
            } catch (org.springframework.dao.DataAccessException ex) {
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
