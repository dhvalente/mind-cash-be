package br.com.mindcash.financial.driven.revision.v1.events;

import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ExpenseRegisteredEvent {

    private ExpenseRegisteredEvent() {}

    public static Map<String, Object> toPayload(ExpenseRegistered event) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("expenseId", event.expenseId().value());
        payload.put("accountId", event.accountId().value());
        payload.put("description", event.description().value());
        payload.put("type", event.type().toString());
        payload.put("category", event.category().toString());
        payload.put("status", event.status().toString());
        payload.put("amount", event.amount().value().toString());
        payload.put("currency", event.amount().currency().getCurrencyCode());
        payload.put("timestamp", event.expenseInstant().value().toString());
        return payload;
    }
}

