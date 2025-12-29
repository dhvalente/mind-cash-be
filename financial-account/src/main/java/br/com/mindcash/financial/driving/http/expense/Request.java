package br.com.mindcash.financial.driving.http.expense;


import br.com.mindcash.financial.application.commands.RegisterExpense;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.expense.*;
import br.com.mindcash.financial.driving.http.expense.jsons.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

@JsonSerialize
public record Request(
        String accountId,
        String description,
        TypeJson type,
        CategoryJson category,
        StatusJson status,
        AmountJson amount,
        List<InstallmentJson> installments
) {
    public RegisterExpense toCommand() {
        return new RegisterExpense(
            new AccountId(accountId),
            new Description (description),
            Type.valueOf(type.name()),
            Category.valueOf(category.description()),
            Status.valueOf(status.toString()),
            new Amount (amount.value(), amount.currency()),
            installments.stream().map(installmentJson -> new Installment(installmentJson.installmentNumber(), installmentJson.installmentAmount(), installmentJson.dueDate())).toList(
        ));
    }
}