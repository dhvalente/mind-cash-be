-- sql
CREATE TABLE IF NOT EXISTS account (
    idt_account BINARY(16) NOT NULL COMMENT 'UUID que identifica a conta',
    des_account VARCHAR(255) NOT NULL COMMENT 'Descrição/nome da conta',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Data/hora de criação do registro',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data/hora da última atualização',
    CONSTRAINT ACCOUNT_PK PRIMARY KEY (idt_account)
) COMMENT='Tabela que armazena contas (account).';

CREATE INDEX idx_account_id ON account (idt_account);
