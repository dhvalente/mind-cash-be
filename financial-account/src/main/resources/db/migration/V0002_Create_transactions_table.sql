CREATE TABLE transactions (
    idt_transaction BINARY(16) COMMENT 'UUID que identifica a movimentação financeira',
    idt_transaction_type VARCHAR(10) NOT NULL COMMENT 'Tipo da movimentação financeira (crédito ou débito)',
    dat_transaction DATETIME(6) NOT NULL COMMENT 'Momento em que a transação foi realizada',
    idt_account BINARY(16) NOT NULL COMMENT 'UUID que identifica a conta associada à transação',
    val_amount DECIMAL(15, 3) NOT NULL COMMENT 'Valor da transação (sempre positivo)',
    des_transaction VARCHAR(255) NOT NULL COMMENT 'Descrição detalhada da transação',
    ind_category VARCHAR(50) NOT NULL COMMENT 'Categoria da transação (ex: SALÁRIO, ALIMENTAÇÃO)',
    ind_status VARCHAR(20) NOT NULL COMMENT 'Status da transação (ex: PAGO, RECEBIDO)',
    cod_currency CHAR(3) NOT NULL COMMENT 'Código da moeda (ex: BRL, USD)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Data/hora de criação do registro',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data/hora da última atualização',
    CONSTRAINT TRANSACTIONS_PK PRIMARY KEY (idt_transaction),
    CONSTRAINT TRANSACTIONS_ACCOUNT_FK FOREIGN KEY (idt_account) REFERENCES account(idt_account)
) COMMENT='Esta tabela armazena todas as movimentações financeiras (receitas e despesas) realizadas pelas contas do sistema.';
