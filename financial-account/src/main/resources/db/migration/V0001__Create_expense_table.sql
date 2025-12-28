-- sql
CREATE TABLE IF NOT EXISTS expense (
    idt_expense BINARY(16) NOT NULL COMMENT 'UUID que identifica a despesa',
    idt_account BINARY(16) NOT NULL COMMENT 'UUID da conta associada à despesa',
    dat_expense DATETIME(6) NOT NULL COMMENT 'Momento em que a despesa foi registrada (instant)',
    des_expense VARCHAR(255) NOT NULL COMMENT 'Descrição detalhada da despesa',
    ind_category VARCHAR(50) NOT NULL COMMENT 'Categoria da despesa (ex: ALIMENTAÇÃO, TRANSPORTE)',
    ind_status VARCHAR(20) NOT NULL COMMENT 'Status da despesa (ex: PAGO, PENDENTE)',
    val_amount DECIMAL(15,3) NOT NULL COMMENT 'Valor da despesa (sempre positivo)',
    cod_currency CHAR(3) NOT NULL COMMENT 'Código da moeda (ex: BRL, USD)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Data/hora de criação do registro',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data/hora da última atualização',
    CONSTRAINT EXPENSE_PK PRIMARY KEY (idt_expense),
    CONSTRAINT EXPENSE_ACCOUNT_FK FOREIGN KEY (idt_account) REFERENCES account(idt_account)
    ) COMMENT='Tabela que armazena despesas (expense) associadas às contas.';

CREATE INDEX idx_expense_account ON expense (idt_account);
