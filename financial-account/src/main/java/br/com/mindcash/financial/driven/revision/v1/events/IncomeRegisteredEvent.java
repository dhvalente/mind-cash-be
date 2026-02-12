package br.com.mindcash.financial.driven.revision.v1.events;

import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import java.util.LinkedHashMap;
import java.util.Map;

public final class IncomeRegisteredEvent {

    private IncomeRegisteredEvent() {}

    public static Map<String, Object> toPayload(IncomeRegistered event) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("incomeId", event.incomeId().value());
        payload.put("accountId", event.accountId().value());
        payload.put("description", event.description().value());
        payload.put("type", event.type().toString());
        payload.put("category", event.category().toString());
        payload.put("status", event.status().toString());
        payload.put("amount", event.amount().value().toString());
        payload.put("currency", event.amount().currency().getCurrencyCode());
        payload.put("timestamp", event.incomeInstant().value().toString());
        return payload;
    }
}

