-- ========================================
-- SCRIPT DML - POPULAÇÃO E MANIPULAÇÃO DE DADOS
-- Sistema de Transações Financeiras - ORACLE
-- ========================================

-- ========================================
-- INSERT - POPULAÇÃO DAS TABELAS
-- ========================================

-- Inserção de Clientes 
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('João Silva Santos', 'joao.silva@email.com', '12345678901', TO_DATE('1985-03-15', 'YYYY-MM-DD'));
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Maria Oliveira Costa', 'maria.oliveira@email.com', '23456789012', TO_DATE('1990-07-22', 'YYYY-MM-DD'));
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Pedro Ferreira Lima', 'pedro.ferreira@email.com', '34567890123', TO_DATE('1988-11-08', 'YYYY-MM-DD'));
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Ana Carolina Souza', 'ana.souza@email.com', '45678901234', TO_DATE('1992-02-14', 'YYYY-MM-DD'));
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Carlos Eduardo Pereira', 'carlos.pereira@email.com', '56789012345', TO_DATE('1987-09-30', 'YYYY-MM-DD'));
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Fernanda Almeida', 'fernanda.almeida@email.com', '67890123456', TO_DATE('1991-12-05', 'YYYY-MM-DD'));
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Ricardo Barbosa', 'ricardo.barbosa@email.com', '78901234567', TO_DATE('1984-06-18', 'YYYY-MM-DD'));
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Juliana Rodrigues', 'juliana.rodrigues@email.com', '89012345678', TO_DATE('1993-04-25', 'YYYY-MM-DD'));

-- Inserção de Criptomoedas 
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Bitcoin', 'BTC', TO_DATE('2009-01-03', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Ethereum', 'ETH', TO_DATE('2015-07-30', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Cardano', 'ADA', TO_DATE('2017-09-29', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Polygon', 'MATIC', TO_DATE('2017-10-01', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Chainlink', 'LINK', TO_DATE('2017-09-20', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Litecoin', 'LTC', TO_DATE('2011-10-07', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Polkadot', 'DOT', TO_DATE('2020-08-19', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Solana', 'SOL', TO_DATE('2020-03-16', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Avalanche', 'AVAX', TO_DATE('2020-09-21', 'YYYY-MM-DD'));
INSERT INTO CRYPTO (nome, sigla, data_lancamento) VALUES ('Uniswap', 'UNI', TO_DATE('2020-09-16', 'YYYY-MM-DD'));

-- Inserção de Contas 
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (1, '0001234567', '1001', TO_DATE('2023-01-15', 'YYYY-MM-DD'), 50000.00);
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (2, '0002345678', '1001', TO_DATE('2023-02-20', 'YYYY-MM-DD'), 35000.00);
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (3, '0003456789', '1002', TO_DATE('2023-03-10', 'YYYY-MM-DD'), 28000.00);
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (4, '0004567890', '1002', TO_DATE('2023-04-05', 'YYYY-MM-DD'), 42000.00);
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (5, '0005678901', '1003', TO_DATE('2023-05-12', 'YYYY-MM-DD'), 65000.00);
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (6, '0006789012', '1003', TO_DATE('2023-06-18', 'YYYY-MM-DD'), 38000.00);
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (7, '0007890123', '1004', TO_DATE('2023-07-22', 'YYYY-MM-DD'), 22000.00);
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (8, '0008901234', '1004', TO_DATE('2023-08-30', 'YYYY-MM-DD'), 45000.00);
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (1, '0009012345', '1001', TO_DATE('2023-09-15', 'YYYY-MM-DD'), 15000.00); -- João tem segunda conta
INSERT INTO CONTA (CLIENTE_id_cliente, numero_conta, agencia, data_abertura, saldo) VALUES (2, '0010123456', '1002', TO_DATE('2023-10-20', 'YYYY-MM-DD'), 25000.00); -- Maria tem segunda conta

-- Inserção de Carteiras 
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (1);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (2);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (3);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (4);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (5);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (6);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (7);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (8);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (9);
INSERT INTO CARTEIRA (CONTA_id_conta) VALUES (10);

-- Inserção de Posses (Criptomoedas nas Carteiras)
-- Carteira 1 (João)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (1, 1, 0.50000000); -- 0.5 BTC
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (1, 2, 2.45000000); -- 2.45 ETH
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (1, 3, 1000.00000000); -- 1000 ADA

-- Carteira 2 (Maria)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (2, 1, 0.25000000); -- 0.25 BTC
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (2, 4, 500.00000000); -- 500 MATIC
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (2, 5, 25.00000000); -- 25 LINK

-- Carteira 3 (Pedro)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (3, 2, 1.80000000); -- 1.8 ETH
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (3, 6, 5.00000000); -- 5 LTC
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (3, 7, 15.00000000); -- 15 DOT

-- Carteira 4 (Ana)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (4, 8, 10.00000000); -- 10 SOL
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (4, 9, 8.50000000); -- 8.5 AVAX

-- Carteira 5 (Carlos)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (5, 1, 0.75000000); -- 0.75 BTC
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (5, 10, 50.00000000); -- 50 UNI

-- Carteira 6 (Fernanda)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (6, 2, 3.20000000); -- 3.2 ETH
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (6, 3, 2000.00000000); -- 2000 ADA

-- Carteira 7 (Ricardo)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (7, 4, 800.00000000); -- 800 MATIC
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (7, 5, 40.00000000); -- 40 LINK

-- Carteira 8 (Juliana)
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (8, 6, 12.00000000); -- 12 LTC
INSERT INTO POSSE (CARTEIRA_id_carteira, CRYPTO_id_crypto, quantidade_crypto) VALUES (8, 7, 25.00000000); -- 25 DOT

-- Inserção de Transações Fiat 
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (1, 2, 1500.00, TO_TIMESTAMP('2024-01-15 10:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (3, 4, 2500.00, TO_TIMESTAMP('2024-01-16 14:22:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (5, 6, 800.00, TO_TIMESTAMP('2024-01-17 09:15:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (7, 8, 1200.00, TO_TIMESTAMP('2024-01-18 16:45:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (2, 1, 500.00, TO_TIMESTAMP('2024-01-19 11:20:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (4, 3, 1800.00, TO_TIMESTAMP('2024-01-20 13:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (6, 5, 900.00, TO_TIMESTAMP('2024-01-21 15:10:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (8, 7, 1100.00, TO_TIMESTAMP('2024-01-22 08:25:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (1, 3, 2200.00, TO_TIMESTAMP('2024-01-23 12:40:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO TRANSACAO_FIAT (CONTA_id_conta_origem, CONTA_id_conta_destino, valor, data_hora) VALUES (9, 10, 3000.00, TO_TIMESTAMP('2024-01-24 17:55:00', 'YYYY-MM-DD HH24:MI:SS'));

-- Inserção de Transações Crypto 
-- Compras
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (1, 1, 'COMPRA', 0.25000000000, 45000.00000000, TO_TIMESTAMP('2024-01-15 09:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (2, 2, 'COMPRA', 1.50000000000, 2800.00000000, TO_TIMESTAMP('2024-01-15 10:45:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (3, 3, 'COMPRA', 500.000000000000, 0.45000000, TO_TIMESTAMP('2024-01-16 11:20:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (4, 8, 'COMPRA', 5.000000000000, 95.50000000, TO_TIMESTAMP('2024-01-16 14:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (5, 1, 'COMPRA', 0.50000000000, 44500.00000000, TO_TIMESTAMP('2024-01-17 08:15:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');

-- Vendas
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (1, 1, 'VENDA', 0.10000000000, 46000.00000000, TO_TIMESTAMP('2024-01-18 16:20:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (2, 2, 'VENDA', 0.50000000000, 2850.00000000, TO_TIMESTAMP('2024-01-19 13:45:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (3, 3, 'VENDA', 200.000000000000, 0.47000000, TO_TIMESTAMP('2024-01-20 10:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');

-- Transações pendentes e outras
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (6, 4, 'COMPRA', 300.000000000000, 0.85000000, TO_TIMESTAMP('2024-01-21 15:10:00', 'YYYY-MM-DD HH24:MI:SS'), 'PENDENTE');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (7, 5, 'COMPRA', 20.000000000000, 12.50000000, TO_TIMESTAMP('2024-01-22 12:25:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (8, 6, 'COMPRA', 8.000000000000, 85.00000000, TO_TIMESTAMP('2024-01-23 09:40:00', 'YYYY-MM-DD HH24:MI:SS'), 'FALHOU');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (9, 7, 'COMPRA', 12.000000000000, 6.25000000, TO_TIMESTAMP('2024-01-24 14:55:00', 'YYYY-MM-DD HH24:MI:SS'), 'CONCLUIDA');
INSERT INTO TRANSACAO_CRYPTO (CONTA_id_conta, CRYPTO_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, data_hora, status) VALUES (10, 10, 'COMPRA', 25.000000000000, 8.40000000, TO_TIMESTAMP('2024-01-25 11:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'CANCELADA');

-- ========================================
-- UPDATE - EXEMPLOS DE ATUALIZAÇÕES
-- ========================================

-- Atualizar email de um cliente
UPDATE CLIENTE 
SET email = 'joao.silva.novo@email.com' 
WHERE nome = 'João Silva Santos';

-- Atualizar status de uma transação pendente para concluída
UPDATE TRANSACAO_CRYPTO 
SET status = 'CONCLUIDA' 
WHERE status = 'PENDENTE' AND CONTA_id_conta = 6;

-- Atualizar quantidade de crypto em uma posse após compra
UPDATE POSSE 
SET quantidade_crypto = quantidade_crypto + 300.00000000 
WHERE CARTEIRA_id_carteira = 6 AND CRYPTO_id_crypto = 4;

-- Atualizar data de nascimento de um cliente
UPDATE CLIENTE 
SET data_nascimento = TO_DATE('1985-03-20', 'YYYY-MM-DD') 
WHERE nome = 'João Silva Santos';

-- ========================================
-- DELETE - EXEMPLOS DE EXCLUSÕES
-- ========================================

-- Remover uma posse com quantidade zero
DELETE FROM POSSE 
WHERE quantidade_crypto = 0;

-- Cancelar (remover) uma transação que falhou
DELETE FROM TRANSACAO_CRYPTO 
WHERE status = 'FALHOU';

-- Remover transações canceladas antigas (mais de 30 dias)
DELETE FROM TRANSACAO_CRYPTO 
WHERE status = 'CANCELADA' 
AND data_hora < SYSDATE - 30;

-- ========================================
-- SELECT - CONSULTAS DIVERSAS
-- ========================================

-- 1. Listar todos os clientes com suas respectivas contas
SELECT 
    c.nome,
    c.email,
    ct.numero_conta,
    ct.agencia,
    ct.data_abertura,
    ct.saldo
FROM CLIENTE c
INNER JOIN CONTA ct ON c.id_cliente = ct.CLIENTE_id_cliente
ORDER BY c.nome;

-- 2. Mostrar o portfolio de criptomoedas por cliente
SELECT 
    c.nome AS cliente,
    cr.nome AS criptomoeda,
    cr.sigla,
    p.quantidade_crypto,
    ct.numero_conta
FROM CLIENTE c
INNER JOIN CONTA ct ON c.id_cliente = ct.CLIENTE_id_cliente
INNER JOIN CARTEIRA car ON ct.id_conta = car.CONTA_id_conta
INNER JOIN POSSE p ON car.id_carteira = p.CARTEIRA_id_carteira
INNER JOIN CRYPTO cr ON p.CRYPTO_id_crypto = cr.id_crypto
ORDER BY c.nome, cr.nome;

-- 3. Relatório de transações fiat por período
SELECT 
    tf.id_transacao_fiat,
    co.nome AS cliente_origem,
    cto.numero_conta AS conta_origem,
    cd.nome AS cliente_destino,
    ctd.numero_conta AS conta_destino,
    tf.valor,
    tf.data_hora
FROM TRANSACAO_FIAT tf
INNER JOIN CONTA cto ON tf.CONTA_id_conta_origem = cto.id_conta
INNER JOIN CLIENTE co ON cto.CLIENTE_id_cliente = co.id_cliente
INNER JOIN CONTA ctd ON tf.CONTA_id_conta_destino = ctd.id_conta
INNER JOIN CLIENTE cd ON ctd.CLIENTE_id_cliente = cd.id_cliente
WHERE tf.data_hora >= TO_TIMESTAMP('2024-01-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS')
ORDER BY tf.data_hora DESC;

-- 4. Transações de crypto por tipo e status
SELECT 
    c.nome AS cliente,
    cr.nome AS criptomoeda,
    tc.tipo_operacao,
    tc.quantidade_crypto,
    tc.valor_unitario,
    tc.quantidade_crypto * tc.valor_unitario AS valor_total,
    tc.data_hora,
    tc.status
FROM TRANSACAO_CRYPTO tc
INNER JOIN CONTA ct ON tc.CONTA_id_conta = ct.id_conta
INNER JOIN CLIENTE c ON ct.CLIENTE_id_cliente = c.id_cliente
INNER JOIN CRYPTO cr ON tc.CRYPTO_id_crypto = cr.id_crypto
ORDER BY tc.data_hora DESC;

-- 5. Total de criptomoedas por tipo
SELECT 
    cr.nome AS criptomoeda,
    cr.sigla,
    COUNT(p.CARTEIRA_id_carteira) AS total_carteiras,
    SUM(p.quantidade_crypto) AS quantidade_total
FROM CRYPTO cr
LEFT JOIN POSSE p ON cr.id_crypto = p.CRYPTO_id_crypto
GROUP BY cr.id_crypto, cr.nome, cr.sigla
ORDER BY quantidade_total DESC NULLS LAST;

-- 6. Clientes com mais de uma conta
SELECT 
    c.nome,
    c.email,
    COUNT(ct.id_conta) AS total_contas
FROM CLIENTE c
INNER JOIN CONTA ct ON c.id_cliente = ct.CLIENTE_id_cliente
GROUP BY c.id_cliente, c.nome, c.email
HAVING COUNT(ct.id_conta) > 1;

-- 7. Volume de transações por cliente (fiat)
SELECT 
    c.nome,
    COUNT(tf.id_transacao_fiat) AS total_transacoes,
    SUM(CASE WHEN tf.CONTA_id_conta_origem = ct.id_conta THEN tf.valor ELSE 0 END) AS total_enviado,
    SUM(CASE WHEN tf.CONTA_id_conta_destino = ct.id_conta THEN tf.valor ELSE 0 END) AS total_recebido
FROM CLIENTE c
INNER JOIN CONTA ct ON c.id_cliente = ct.CLIENTE_id_cliente
LEFT JOIN TRANSACAO_FIAT tf ON ct.id_conta IN (tf.CONTA_id_conta_origem, tf.CONTA_id_conta_destino)
GROUP BY c.id_cliente, c.nome
ORDER BY total_transacoes DESC;

-- 8. Status das transações de crypto
SELECT 
    status,
    COUNT(*) AS quantidade,
    SUM(quantidade_crypto * valor_unitario) AS valor_total
FROM TRANSACAO_CRYPTO
GROUP BY status
ORDER BY quantidade DESC;

-- 9. Criptomoedas mais negociadas
SELECT 
    cr.nome,
    cr.sigla,
    COUNT(tc.id_transacao_crypto) AS total_transacoes,
    SUM(tc.quantidade_crypto) AS quantidade_total_negociada,
    AVG(tc.valor_unitario) AS valor_medio
FROM CRYPTO cr
INNER JOIN TRANSACAO_CRYPTO tc ON cr.id_crypto = tc.CRYPTO_id_crypto
WHERE tc.status = 'CONCLUIDA'
GROUP BY cr.id_crypto, cr.nome, cr.sigla
ORDER BY total_transacoes DESC;

-- 10. Relatório consolidado por cliente
SELECT 
    c.nome AS cliente,
    c.email,
    COUNT(DISTINCT ct.id_conta) AS total_contas,
    COUNT(DISTINCT car.id_carteira) AS total_carteiras,
    COUNT(DISTINCT p.CRYPTO_id_crypto) AS tipos_crypto_possuidos,
    COALESCE(trans_stats.total_transacoes_crypto, 0) AS total_transacoes_crypto,
    SUM(ct.saldo) AS saldo_total_contas
FROM CLIENTE c
LEFT JOIN CONTA ct ON c.id_cliente = ct.CLIENTE_id_cliente
LEFT JOIN CARTEIRA car ON ct.id_conta = car.CONTA_id_conta
LEFT JOIN POSSE p ON car.id_carteira = p.CARTEIRA_id_carteira
LEFT JOIN (
    SELECT 
        ct.CLIENTE_id_cliente,
        COUNT(tc.id_transacao_crypto) as total_transacoes_crypto
    FROM CONTA ct
    INNER JOIN TRANSACAO_CRYPTO tc ON ct.id_conta = tc.CONTA_id_conta
    GROUP BY ct.CLIENTE_id_cliente
) trans_stats ON c.id_cliente = trans_stats.CLIENTE_id_cliente
GROUP BY c.id_cliente, c.nome, c.email, trans_stats.total_transacoes_crypto
ORDER BY c.nome;

-- 11. Saldo das contas por cliente
SELECT 
    c.nome AS cliente,
    ct.numero_conta,
    ct.agencia,
    ct.saldo,
    ct.data_abertura
FROM CLIENTE c
INNER JOIN CONTA ct ON c.id_cliente = ct.CLIENTE_id_cliente
ORDER BY c.nome, ct.numero_conta;

-- ========================================
-- FIM DO SCRIPT DML
-- ========================================