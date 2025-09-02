-- ========================================
-- SCRIPT DDL - CRIAÇÃO DAS TABELAS
-- Sistema de Transações Financeiras - ORACLE DATABASE
-- ========================================

DECLARE
   table_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(table_not_exist, -00942);
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE TRANSACAO_FIAT';
EXCEPTION
   WHEN table_not_exist THEN NULL;
END;
/

DECLARE
   table_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(table_not_exist, -00942);
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE TRANSACAO_CRYPTO';
EXCEPTION
   WHEN table_not_exist THEN NULL;
END;
/

DECLARE
   table_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(table_not_exist, -00942);
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE POSSE';
EXCEPTION
   WHEN table_not_exist THEN NULL;
END;
/

DECLARE
   table_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(table_not_exist, -00942);
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE CARTEIRA';
EXCEPTION
   WHEN table_not_exist THEN NULL;
END;
/

DECLARE
   table_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(table_not_exist, -00942);
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE CONTA';
EXCEPTION
   WHEN table_not_exist THEN NULL;
END;
/

DECLARE
   table_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(table_not_exist, -00942);
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE CLIENTE';
EXCEPTION
   WHEN table_not_exist THEN NULL;
END;
/

DECLARE
   table_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(table_not_exist, -00942);
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE CRYPTO';
EXCEPTION
   WHEN table_not_exist THEN NULL;
END;
/

-- ========================================
-- TABELA CLIENTE
-- ========================================
CREATE TABLE CLIENTE (
    id_cliente INTEGER NOT NULL,
    nome VARCHAR2(100) NOT NULL,
    email VARCHAR2(150) NOT NULL,
    cpf CHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL,
    CONSTRAINT CLIENTE_PK PRIMARY KEY (id_cliente),
    CONSTRAINT CLIENTE_cpf_email_UN UNIQUE (cpf, email)
);

-- ========================================
-- TABELA CRYPTO
-- ========================================
CREATE TABLE CRYPTO (
    id_crypto INTEGER NOT NULL,
    nome VARCHAR2(80) NOT NULL,
    sigla CHAR(10) NOT NULL,
    data_lancamento DATE NOT NULL,
    CONSTRAINT CRYPTO_PK PRIMARY KEY (id_crypto),
    CONSTRAINT CRYPTO_sigla_UN UNIQUE (sigla)
);

-- ========================================
-- TABELA CONTA
-- ========================================
CREATE TABLE CONTA (
    id_conta INTEGER NOT NULL,
    CLIENTE_id_cliente INTEGER NOT NULL,
    numero_conta CHAR(10) NOT NULL,
    agencia CHAR(4) NOT NULL,
    data_abertura DATE NOT NULL,
    CONSTRAINT CONTA_PK PRIMARY KEY (id_conta),
    CONSTRAINT CONTA_numero_conta_agencia_UN UNIQUE (numero_conta, agencia),
    CONSTRAINT CONTA_CLIENTE_FK FOREIGN KEY (CLIENTE_id_cliente) 
        REFERENCES CLIENTE(id_cliente)
);

-- Índice para otimizar consultas por cliente
CREATE INDEX CONTA_IDX ON CONTA (CLIENTE_id_cliente);

-- ========================================
-- TABELA CARTEIRA
-- ========================================
CREATE TABLE CARTEIRA (
    id_carteira INTEGER NOT NULL,
    CONTA_id_conta INTEGER NOT NULL,
    CONSTRAINT CARTEIRA_PK PRIMARY KEY (id_carteira),
    CONSTRAINT CARTEIRA_CONTA_FK FOREIGN KEY (CONTA_id_conta) 
        REFERENCES CONTA(id_conta) ON DELETE CASCADE
);

-- Índice para otimizar consultas por conta
CREATE INDEX CARTEIRA_IDX ON CARTEIRA (CONTA_id_conta);

-- ========================================
-- TABELA POSSE
-- ========================================
CREATE TABLE POSSE (
    CARTEIRA_id_carteira INTEGER NOT NULL,
    CRYPTO_id_crypto INTEGER NOT NULL,
    quantidade_crypto NUMBER(18,8) DEFAULT 0 NOT NULL,
    CONSTRAINT POSSE_PK PRIMARY KEY (CARTEIRA_id_carteira, CRYPTO_id_crypto),
    CONSTRAINT POSSE_CARTEIRA_FK FOREIGN KEY (CARTEIRA_id_carteira) 
        REFERENCES CARTEIRA(id_carteira) ON DELETE CASCADE,
    CONSTRAINT POSSE_CRYPTO_FK FOREIGN KEY (CRYPTO_id_crypto) 
        REFERENCES CRYPTO(id_crypto),
    CONSTRAINT POSSE_quantidade_CHECK CHECK (quantidade_crypto >= 0)
);

-- ========================================
-- TABELA TRANSACAO_FIAT
-- ========================================
CREATE TABLE TRANSACAO_FIAT (
    id_transacao_fiat INTEGER NOT NULL,
    CONTA_id_conta_origem INTEGER NOT NULL,
    CONTA_id_conta_destino INTEGER NOT NULL,
    valor NUMBER(18,2) NOT NULL,
    data_hora TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT TRANSACAO_FIAT_PK PRIMARY KEY (id_transacao_fiat),
    CONSTRAINT TRANSACAO_FIAT_CONTA_FK FOREIGN KEY (CONTA_id_conta_origem) 
        REFERENCES CONTA(id_conta),
    CONSTRAINT TRANSACAO_FIAT_CONTA_FKv1 FOREIGN KEY (CONTA_id_conta_destino) 
        REFERENCES CONTA(id_conta),
    CONSTRAINT TRANSACAO_FIAT_valor_CHECK CHECK (valor > 0),
    CONSTRAINT TRANSACAO_FIAT_contas_diferentes_CHECK CHECK (CONTA_id_conta_origem != CONTA_id_conta_destino)
);

-- ========================================
-- TABELA TRANSACAO_CRYPTO
-- ========================================
CREATE TABLE TRANSACAO_CRYPTO (
    id_transacao_crypto INTEGER NOT NULL,
    CONTA_id_conta INTEGER NOT NULL,
    CRYPTO_id_crypto INTEGER NOT NULL,
    tipo_operacao VARCHAR2(10) NOT NULL,
    quantidade_crypto NUMBER(30,12) NOT NULL,
    valor_unitario NUMBER(18,8) NOT NULL,
    data_hora TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    status VARCHAR2(15) DEFAULT 'PENDENTE' NOT NULL,
    CONSTRAINT TRANSACAO_CRYPTO_PK PRIMARY KEY (id_transacao_crypto),
    CONSTRAINT TRANSACAO_CRYPTO_CONTA_FK FOREIGN KEY (CONTA_id_conta) 
        REFERENCES CONTA(id_conta),
    CONSTRAINT TRANSACAO_CRYPTO_CRYPTO_FK FOREIGN KEY (CRYPTO_id_crypto) 
        REFERENCES CRYPTO(id_crypto),
    CONSTRAINT TRANSACAO_CRYPTO_tipo_CHECK CHECK (tipo_operacao IN ('COMPRA', 'VENDA')),
    CONSTRAINT TRANSACAO_CRYPTO_quantidade_CHECK CHECK (quantidade_crypto > 0),
    CONSTRAINT TRANSACAO_CRYPTO_valor_CHECK CHECK (valor_unitario > 0),
    CONSTRAINT TRANSACAO_CRYPTO_status_CHECK CHECK (status IN ('PENDENTE', 'CONCLUIDA', 'CANCELADA', 'FALHOU'))
);

-- ========================================
-- COMENTÁRIOS DAS TABELAS
-- ========================================
COMMENT ON TABLE CLIENTE IS 'Tabela de clientes do sistema';
COMMENT ON TABLE CRYPTO IS 'Tabela de criptomoedas disponíveis';
COMMENT ON TABLE CONTA IS 'Tabela de contas bancárias dos clientes';
COMMENT ON TABLE CARTEIRA IS 'Tabela de carteiras digitais vinculadas às contas';
COMMENT ON TABLE POSSE IS 'Tabela de posses de criptomoedas por carteira';
COMMENT ON TABLE TRANSACAO_FIAT IS 'Tabela de transações em moeda fiduciária';
COMMENT ON TABLE TRANSACAO_CRYPTO IS 'Tabela de transações de criptomoedas';

-- ========================================
-- COMENTÁRIOS DAS COLUNAS PRINCIPAIS
-- ========================================
COMMENT ON COLUMN CLIENTE.cpf IS 'CPF do cliente (apenas números)';
COMMENT ON COLUMN CONTA.numero_conta IS 'Número da conta bancária';
COMMENT ON COLUMN CONTA.agencia IS 'Código da agência';
COMMENT ON COLUMN POSSE.quantidade_crypto IS 'Quantidade de criptomoeda possuída';
COMMENT ON COLUMN TRANSACAO_CRYPTO.tipo_operacao IS 'Tipo da operação: COMPRA ou VENDA';
COMMENT ON COLUMN TRANSACAO_CRYPTO.status IS 'Status da transação';

-- ========================================
-- REMOVER SEQUENCES EXISTENTES (se houver)
-- ========================================
DECLARE
   seq_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(seq_not_exist, -02289);
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_CLIENTE';
EXCEPTION
   WHEN seq_not_exist THEN NULL;
END;
/

DECLARE
   seq_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(seq_not_exist, -02289);
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_CRYPTO';
EXCEPTION
   WHEN seq_not_exist THEN NULL;
END;
/

DECLARE
   seq_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(seq_not_exist, -02289);
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_CONTA';
EXCEPTION
   WHEN seq_not_exist THEN NULL;
END;
/

DECLARE
   seq_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(seq_not_exist, -02289);
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_CARTEIRA';
EXCEPTION
   WHEN seq_not_exist THEN NULL;
END;
/

DECLARE
   seq_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(seq_not_exist, -02289);
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_TRANSACAO_FIAT';
EXCEPTION
   WHEN seq_not_exist THEN NULL;
END;
/

DECLARE
   seq_not_exist EXCEPTION;
   PRAGMA EXCEPTION_INIT(seq_not_exist, -02289);
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_TRANSACAO_CRYPTO';
EXCEPTION
   WHEN seq_not_exist THEN NULL;
END;
/

-- ========================================
-- SEQUÊNCIAS PARA AUTO INCREMENT (Oracle não tem SERIAL)
-- ========================================
CREATE SEQUENCE SEQ_CLIENTE START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_CRYPTO START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_CONTA START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_CARTEIRA START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_TRANSACAO_FIAT START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_TRANSACAO_CRYPTO START WITH 1 INCREMENT BY 1;

-- ========================================
-- TRIGGERS PARA AUTO INCREMENT (OPCIONAL)
-- ========================================
CREATE OR REPLACE TRIGGER TRG_CLIENTE_ID
    BEFORE INSERT ON CLIENTE
    FOR EACH ROW
    WHEN (NEW.id_cliente IS NULL)
BEGIN
    :NEW.id_cliente := SEQ_CLIENTE.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER TRG_CRYPTO_ID
    BEFORE INSERT ON CRYPTO
    FOR EACH ROW
    WHEN (NEW.id_crypto IS NULL)
BEGIN
    :NEW.id_crypto := SEQ_CRYPTO.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER TRG_CONTA_ID
    BEFORE INSERT ON CONTA
    FOR EACH ROW
    WHEN (NEW.id_conta IS NULL)
BEGIN
    :NEW.id_conta := SEQ_CONTA.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER TRG_CARTEIRA_ID
    BEFORE INSERT ON CARTEIRA
    FOR EACH ROW
    WHEN (NEW.id_carteira IS NULL)
BEGIN
    :NEW.id_carteira := SEQ_CARTEIRA.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER TRG_TRANSACAO_FIAT_ID
    BEFORE INSERT ON TRANSACAO_FIAT
    FOR EACH ROW
    WHEN (NEW.id_transacao_fiat IS NULL)
BEGIN
    :NEW.id_transacao_fiat := SEQ_TRANSACAO_FIAT.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER TRG_TRANSACAO_CRYPTO_ID
    BEFORE INSERT ON TRANSACAO_CRYPTO
    FOR EACH ROW
    WHEN (NEW.id_transacao_crypto IS NULL)
BEGIN
    :NEW.id_transacao_crypto := SEQ_TRANSACAO_CRYPTO.NEXTVAL;
END;
/

-- ========================================
-- FIM DO SCRIPT DDL - ORACLE
-- ========================================