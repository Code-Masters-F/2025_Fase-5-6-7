-- ========================================
-- SCRIPT DML - POPULAÇÃO E MANIPULAÇÃO DE DADOS (COM COMMIT)
-- ========================================

-- =========================
-- 1. INSERÇÃO DE CLIENTES
-- =========================
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('João Silva Santos', 'joao.silva@email.com', '12345678901', DATE '1985-03-15');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Maria Oliveira Costa', 'maria.oliveira@email.com', '23456789012', DATE '1990-07-22');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Pedro Ferreira Lima', 'pedro.ferreira@email.com', '34567890123', DATE '1988-11-08');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Ana Carolina Souza', 'ana.souza@email.com', '45678901234', DATE '1992-02-14');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Carlos Eduardo Pereira', 'carlos.pereira@email.com', '56789012345', DATE '1987-09-30');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Fernanda Almeida', 'fernanda.almeida@email.com', '67890123456', DATE '1991-12-05');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Ricardo Barbosa', 'ricardo.barbosa@email.com', '78901234567', DATE '1984-06-18');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Juliana Rodrigues', 'juliana.rodrigues@email.com', '89012345678', DATE '1993-04-25');

COMMIT;

-- =========================
-- 2. INSERÇÃO DE CRYPTOS
-- =========================
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Bitcoin',   'BTC',  DATE '2009-01-03');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Ethereum',  'ETH',  DATE '2015-07-30');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Cardano',   'ADA',  DATE '2017-09-29');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Polygon',   'MATIC',DATE '2017-10-01');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Chainlink', 'LINK', DATE '2017-09-20');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Litecoin',  'LTC',  DATE '2011-10-07');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Polkadot',  'DOT',  DATE '2020-08-19');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Solana',    'SOL',  DATE '2020-03-16');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Avalanche', 'AVAX', DATE '2020-09-21');
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Uniswap',   'UNI',  DATE '2020-09-16');

COMMIT;

-- =========================
-- 3. INSERÇÃO DE CONTAS
-- =========================
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0001234567', '1001', DATE '2023-01-15', 50000 FROM CLIENTE WHERE nome = 'João Silva Santos';

INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0002345678', '1001', DATE '2023-02-20', 50000 FROM CLIENTE WHERE nome = 'Maria Oliveira Costa';

INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0003456789', '1002', DATE '2023-03-10', 50000 FROM CLIENTE WHERE nome = 'Pedro Ferreira Lima';

INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0004567890', '1002', DATE '2023-04-05', 50000 FROM CLIENTE WHERE nome = 'Ana Carolina Souza';

INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0005678901', '1003', DATE '2023-05-12', 50000 FROM CLIENTE WHERE nome = 'Carlos Eduardo Pereira';

INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0006789012', '1003', DATE '2023-06-18', 50000 FROM CLIENTE WHERE nome = 'Fernanda Almeida';

INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0007890123', '1004', DATE '2023-07-22', 50000 FROM CLIENTE WHERE nome = 'Ricardo Barbosa';

INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0008901234', '1004', DATE '2023-08-30', 50000 FROM CLIENTE WHERE nome = 'Juliana Rodrigues';

-- João segunda conta
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0009012345', '1001', DATE '2023-09-15', 50000 FROM CLIENTE WHERE nome = 'João Silva Santos';

-- Maria segunda conta
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0010123456', '1002', DATE '2023-10-20', 50000 FROM CLIENTE WHERE nome = 'Maria Oliveira Costa';

COMMIT;

-- =========================
-- 4. INSERÇÃO DE CARTEIRAS
-- =========================
INSERT INTO CARTEIRA (CONTA_id_conta)
SELECT id_conta FROM CONTA;

COMMIT;

-- =========================
-- 5. INSERÇÃO DE POSSES (EXEMPLOS)
-- =========================

-- Carteira João Silva Santos (BTC, ETH, ADA)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 0.5
FROM CARTEIRA ca
JOIN CONTA ct ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM=1;

COMMIT;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 0.25, 45000, TIMESTAMP '2024-01-15 09:30:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM=1;

COMMIT;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 2.45
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 1000
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ADA'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

-- -------------------------
-- Maria Oliveira Costa: BTC, MATIC, LINK
-- -------------------------
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 0.25
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 500
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'MATIC'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 25
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'LINK'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

-- -------------------------
-- Pedro Ferreira Lima: ETH, LTC, DOT
-- -------------------------
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 1.8
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 5
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'LTC'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 15
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'DOT'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

-- -------------------------
-- Ana Carolina Souza: SOL, AVAX
-- -------------------------
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 10
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'SOL'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 8.5
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'AVAX'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

-- -------------------------
-- Carlos Eduardo Pereira: BTC, UNI
-- -------------------------
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 0.75
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 50
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'UNI'
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;

-- -------------------------
-- Fernanda Almeida: ETH, ADA
-- -------------------------
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 3.2
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Fernanda Almeida' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 2000
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ADA'
WHERE cl.nome = 'Fernanda Almeida' AND ROWNUM = 1;

-- -------------------------
-- Ricardo Barbosa: MATIC, LINK
-- -------------------------
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 800
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'MATIC'
WHERE cl.nome = 'Ricardo Barbosa' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 40
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'LINK'
WHERE cl.nome = 'Ricardo Barbosa' AND ROWNUM = 1;

-- -------------------------
-- Juliana Rodrigues: LTC, DOT
-- -------------------------
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 12
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'LTC'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto)
SELECT ca.id_carteira, cr.id_crypto, 25
FROM CARTEIRA ca
JOIN CONTA ct   ON ca.CONTA_id_conta = ct.id_conta
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'DOT'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

COMMIT;

-- =========================
-- 6. INSERÇÃO DE TRANSACOES CRYPTO (EXEMPLO)
-- =========================

-- =========================
-- 6. INSERÇÃO DE TRANSACOES CRYPTO
-- =========================

-- COMPRAS (debitam saldo)
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 0.25, 45000, TIMESTAMP '2024-01-15 09:30:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 1.5, 2800, TIMESTAMP '2024-01-15 10:45:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 500, 0.45, TIMESTAMP '2024-01-16 11:20:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ADA'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 5, 95.5, TIMESTAMP '2024-01-16 14:30:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'SOL'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 0.5, 44500, TIMESTAMP '2024-01-17 08:15:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;

-- VENDAS (creditam saldo)
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'VENDA', 0.1, 46000, TIMESTAMP '2024-01-18 16:20:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'VENDA', 0.5, 2850, TIMESTAMP '2024-01-19 13:45:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'VENDA', 200, 0.47, TIMESTAMP '2024-01-20 10:30:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'ADA'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

-- OUTRAS: pendente / falhou / cancelada
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 300, 0.85, TIMESTAMP '2024-01-21 15:10:00', 'PENDENTE'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'MATIC'
WHERE cl.nome = 'Fernanda Almeida' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 20, 12.5, TIMESTAMP '2024-01-22 12:25:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'LINK'
WHERE cl.nome = 'Ricardo Barbosa' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 8, 85, TIMESTAMP '2024-01-23 09:40:00', 'FALHOU'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'LTC'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 12, 6.25, TIMESTAMP '2024-01-24 14:55:00', 'CONCLUIDA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'DOT'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status)
SELECT ct.id_conta, cr.id_crypto, 'COMPRA', 25, 8.4, TIMESTAMP '2024-01-25 11:30:00', 'CANCELADA'
FROM CONTA ct
JOIN CLIENTE cl ON ct.CLIENTE_id_cliente = cl.id_cliente
JOIN CRYPTO  cr ON cr.sigla = 'UNI'
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;

COMMIT;



-- ========================================
-- FIM DO SCRIPT DML
-- ========================================
