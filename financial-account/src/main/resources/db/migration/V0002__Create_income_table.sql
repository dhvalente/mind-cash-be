CREATE TABLE IF NOT EXISTS income (
    idt_income BINARY(16) NOT NULL COMMENT 'UUID que identifica a receita',
    idt_account BINARY(16) NOT NULL COMMENT 'UUID da conta associada à receita',
    dat_income DATETIME(6) NOT NULL COMMENT 'Momento em que a receita foi registrada (instant)',
    des_income VARCHAR(255) NOT NULL COMMENT 'Descrição detalhada da receita',
    ind_type VARCHAR(50) NOT NULL COMMENT 'Tipo da receita (ex: Fixa, Variável)',
    ind_category VARCHAR(50) NOT NULL COMMENT 'Categoria da receita (ex: SALARIO, BONUS)',
    ind_status VARCHAR(20) NOT NULL COMMENT 'Status da receita (ex: RECEBIDO, PENDENTE)',
    val_amount DECIMAL(15,3) NOT NULL COMMENT 'Valor da receita (sempre positivo)',
    cod_currency CHAR(3) NOT NULL COMMENT 'Código da moeda (ex: BRL, USD)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Data/hora de criação do registro',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data/hora da última atualização',
    CONSTRAINT INCOME_PK PRIMARY KEY (idt_income),
    CONSTRAINT INCOME_ACCOUNT_FK FOREIGN KEY (idt_account) REFERENCES account(idt_account)
    ) COMMENT='Tabela que armazena receitas (income) associadas às contas.';

CREATE INDEX idx_income_account ON income (idt_account);
