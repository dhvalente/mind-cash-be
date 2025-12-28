package br.com.mindcash.financial.application.domain.events;

import br.com.mindcash.financial.driven.account.statements.expense.ExpenseRegisteredStatement;
import java.util.List;

public sealed interface AccountEvent permits ExpenseRegistered, IncomeRegistered {

            record Statement(String template, Object... args) {}

            default List<Statement> statements() {
                return switch (this) {
                    case ExpenseRegistered ev -> List.of(ExpenseRegisteredStatement.from(ev));
                    default -> throw new IllegalStateException("Unexpected value: " + this);
                };
            }
}
