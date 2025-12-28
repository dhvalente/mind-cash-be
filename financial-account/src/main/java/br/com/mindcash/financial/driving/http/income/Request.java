/*package br.com.mindcash.financial.driving.http.income;


import br.com.mindcash.financial.application.commands.RegisterIncome;
import br.com.mindcash.financial.driving.http.income.jsons.CategoryJson;
import br.com.mindcash.financial.driving.http.income.jsons.InstallmentJson;
import br.com.mindcash.financial.driving.http.income.jsons.StatusJson;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@JsonSerialize
public record Request(
        String description,
        StatusJson status,
        BigDecimal amount,
        CategoryJson category,
        LocalDate date,
        List<InstallmentJson> installments,
        String accountId
) {
    public RegisterIncome toCommand() {
        return new RegisterIncome(

        );
    }
}*/