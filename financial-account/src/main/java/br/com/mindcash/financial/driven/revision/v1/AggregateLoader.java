package br.com.mindcash.financial.driven.revision.v1;

import br.com.mindcash.financial.application.domain.models.account.AccountId;
import br.com.mindcash.financial.driven.revision.v1.aggregate.AggregateV1;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;

/**
 * Carrega o estado atual do agregado a partir dos snapshots na outbox.
 */
@Component
public class AggregateLoader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String FIND_LATEST_SNAPSHOT = """
        SELECT jsn_aggregate_snapshot, num_revision
        FROM outbox
        WHERE cod_aggregate = ? AND nam_aggregate = 'Account'
        ORDER BY num_revision DESC
        LIMIT 1
        """;

    private final JdbcTemplate jdbcTemplate;

    public AggregateLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AggregateV1 load(AccountId accountId) {
        List<Map<String, Object>> results = jdbcTemplate.queryForList(
            FIND_LATEST_SNAPSHOT,
            accountId.value()
        );

        if (results.isEmpty()) {
            return AggregateV1.empty(accountId);
        }

        Map<String, Object> row = results.getFirst();
        String snapshotJson = (String) row.get("jsn_aggregate_snapshot");

        return parseSnapshot(snapshotJson, accountId);
    }

    private AggregateV1 parseSnapshot(String json, AccountId accountId) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            BigDecimal balance = new BigDecimal(node.get("balance").asText());
            Currency currency = Currency.getInstance(node.get("currency").asText());
            int revision = node.get("revision").asInt();

            return new AggregateV1(accountId, balance, currency, revision);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse aggregate snapshot", e);
        }
    }
}


