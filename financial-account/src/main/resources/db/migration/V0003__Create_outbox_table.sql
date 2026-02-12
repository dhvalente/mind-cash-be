CREATE TABLE IF NOT EXISTS outbox (
    idt_outbox_event BINARY(16) NOT NULL COMMENT 'UUID que identifica o evento na outbox',
    nam_event VARCHAR(100) NOT NULL COMMENT 'Nome do evento (ex: ExpenseRegistered, IncomeRegistered)',
    dat_event DATETIME(6) NOT NULL COMMENT 'Momento em que o evento ocorreu',
    jsn_event_payload JSON NOT NULL COMMENT 'Payload do evento em formato JSON',
    cod_aggregate VARCHAR(36) NOT NULL COMMENT 'Código/ID do agregado (UUID como string)',
    nam_aggregate VARCHAR(100) NOT NULL COMMENT 'Nome do agregado (ex: Account)',
    jsn_aggregate_snapshot JSON NOT NULL COMMENT 'Snapshot do estado do agregado após o evento',
    num_revision INT NOT NULL COMMENT 'Número da revisão/versão do agregado',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Data/hora de criação do registro',
    CONSTRAINT OUTBOX_PK PRIMARY KEY (idt_outbox_event)
) COMMENT='Tabela Outbox para persistência de eventos de domínio com snapshots de agregados.';

CREATE INDEX idx_outbox_aggregate ON outbox (cod_aggregate, nam_aggregate);
CREATE INDEX idx_outbox_event_name ON outbox (nam_event);
CREATE INDEX idx_outbox_revision ON outbox (cod_aggregate, num_revision);

