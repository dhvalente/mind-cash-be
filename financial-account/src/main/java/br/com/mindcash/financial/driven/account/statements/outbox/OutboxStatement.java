package br.com.mindcash.financial.driven.account.statements.outbox;

import br.com.mindcash.financial.application.domain.events.AccountEvent;
import br.com.mindcash.financial.application.domain.events.ExpenseRegistered;
import br.com.mindcash.financial.application.domain.events.IncomeRegistered;
import br.com.mindcash.financial.driven.account.statements.Statement;
import br.com.mindcash.financial.driven.revision.v1.aggregate.AggregateV1;
import br.com.mindcash.financial.driven.revision.v1.aggregate.AggregateV1Snapshot;
import br.com.mindcash.financial.driven.revision.v1.events.ExpenseRegisteredEvent;
import br.com.mindcash.financial.driven.revision.v1.events.IncomeRegisteredEvent;
import br.com.mindcash.financial.driven.revision.v1.events.RevisionEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.Map;

public final class OutboxStatement {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private OutboxStatement() {}

    public static Statement from(AccountEvent event, AggregateV1 aggregate) {
        RevisionEvent revisionEvent = RevisionEvent.from(event);

        String sql = "INSERT INTO outbox (" +
                "idt_outbox_event, nam_event, dat_event, jsn_event_payload, " +
                "cod_aggregate, nam_aggregate, jsn_aggregate_snapshot, num_revision" +
                ") VALUES (" +
                "UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?, ?" +
                ")";

        Map<String, Object> eventPayload = extractEventPayload(event);
        Map<String, Object> aggregateSnapshot = AggregateV1Snapshot.toSnapshot(aggregate);

        Object[] args = new Object[] {
                revisionEvent.eventId().toString(),
                revisionEvent.eventName(),
                Timestamp.from(revisionEvent.eventTimestamp()),
                toJson(eventPayload),
                aggregate.aggregateCode(),
                aggregate.aggregateName(),
                toJson(aggregateSnapshot),
                aggregate.revision()
        };

        return new Statement(sql, args);
    }

    private static Map<String, Object> extractEventPayload(AccountEvent event) {
        return switch (event) {
            case ExpenseRegistered ev -> ExpenseRegisteredEvent.toPayload(ev);
            case IncomeRegistered ev -> IncomeRegisteredEvent.toPayload(ev);
        };
    }

    private static String toJson(Map<String, Object> map) {
        try {
            return OBJECT_MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize to JSON", e);
        }
    }
}

