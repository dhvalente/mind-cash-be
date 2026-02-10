package br.com.mindcash.financial.driving.http.income;


import br.com.mindcash.financial.application.commands.RegisterIncome;
import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.application.domain.models.income.*;
import br.com.mindcash.financial.driving.http.income.jsons.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.util.List;

@JsonSerialize
public record Request(
        String accountId,
        String description,
        TypeJson type,
        CategoryJson category,
        StatusJson status,
        AmountJson amount,
        LocalDate date,
        List<InstallmentJson> installments
) {
    public RegisterIncome toCommand() {
        return new RegisterIncome(
                new AccountId(accountId),
                new Description(description),
                Type.valueOf(type.name()),
                Category.valueOf(category.description()),
                Status.valueOf(status.toString()),
                new Amount(amount.value(), amount.currency())
        );
    }
}