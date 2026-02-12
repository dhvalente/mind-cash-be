package br.com.mindcash.financial.driven.revision.v1.aggregate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Serializa o AggregateV1 para formato JSON (Map) para persistência como snapshot.
 */
public final class AggregateV1Snapshot {

    private AggregateV1Snapshot() {}

    public static Map<String, Object> toSnapshot(AggregateV1 aggregate) {
        Map<String, Object> snapshot = new LinkedHashMap<>();
        snapshot.put("accountId", aggregate.accountId().value());
        snapshot.put("balance", aggregate.balance().toString());
        snapshot.put("currency", aggregate.currency().getCurrencyCode());
        snapshot.put("revision", aggregate.revision());
        return snapshot;
    }
}

