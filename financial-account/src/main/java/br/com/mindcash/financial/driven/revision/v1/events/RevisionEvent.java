package br.com.mindcash.financial.driven.revision.v1.events;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import java.time.Instant;
import java.util.UUID;

public record RevisionEvent(
    UUID eventId,
    String eventName,
    Instant eventTimestamp,
    AccountEvent domainEvent
) {

    public static RevisionEvent from(AccountEvent event) {
        String eventName = extractEventName(event);
        Instant timestamp = extractTimestamp(event);
        return new RevisionEvent(UUID.randomUUID(), eventName, timestamp, event);
    }

    private static String extractEventName(AccountEvent event) {
        return switch (event) {
            case ExpenseRegistered ignored -> "ExpenseRegistered";
            case IncomeRegistered ignored -> "IncomeRegistered";
        };
    }

    private static Instant extractTimestamp(AccountEvent event) {
        return switch (event) {
            case ExpenseRegistered ev -> ev.expenseInstant().value();
            case IncomeRegistered ev -> ev.incomeInstant().value();
        };
    }
}

