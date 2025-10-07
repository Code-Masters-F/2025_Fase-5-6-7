-- ========================================
-- SCRIPT DML - POPULAÇÃO
-- Regras aplicadas:
--  - Apenas 1 CONTA_INTERNA por cliente
--  - Apenas 2 transações para a Maria (CRIPTO: COMPRA e VENDA de ETH)
-- ========================================

-- 1) CLIENTES
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('João Silva Santos', 'joao.silva@email.com', '12345678901', DATE '1985-03-15');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Maria Oliveira Costa', 'maria.oliveira@email.com', '23456789012', DATE '1990-07-22');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Pedro Ferreira Lima', 'pedro.ferreira@email.com', '34567890123', DATE '1988-11-08');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Ana Carolina Souza', 'ana.souza@email.com', '45678901234', DATE '1992-02-14');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Carlos Eduardo Pereira', 'carlos.pereira@email.com', '56789012345', DATE '1987-09-30');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Fernanda Almeida', 'fernanda.almeida@email.com', '67890123456', DATE '1991-12-05');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Ricardo Barbosa', 'ricardo.barbosa@email.com', '78901234567', DATE '1984-06-18');
INSERT INTO CLIENTE (nome, email, cpf, data_nascimento) VALUES ('Juliana Rodrigues', 'juliana.rodrigues@email.com', '89012345678', DATE '1993-04-25');
COMMIT;

-- 2) CRIPTOMOEDAS
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Bitcoin', 'BTC', DATE '2009-01-03');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Ethereum', 'ETH', DATE '2015-07-30');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Cardano', 'ADA', DATE '2017-09-29');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Polygon', 'MATIC', DATE '2017-10-01');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Chainlink', 'LINK', DATE '2017-09-20');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Litecoin', 'LTC', DATE '2011-10-07');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Polkadot', 'DOT', DATE '2020-08-19');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Solana', 'SOL', DATE '2020-03-16');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Avalanche', 'AVAX', DATE '2020-09-21');
INSERT INTO CRIPTOMOEDA (nome, sigla, data_lancamento) VALUES ('Uniswap', 'UNI', DATE '2020-09-16');
COMMIT;

-- 3) CONTAS EXTERNAS
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '12345-6', '1234', '001', 'Banco do Brasil S.A.' FROM CLIENTE WHERE nome = 'João Silva Santos';

INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '23456-7', '2345', '341', 'Itaú Unibanco S.A.' FROM CLIENTE WHERE nome = 'Maria Oliveira Costa';

INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '34567-8', '3456', '237', 'Banco Bradesco S.A.' FROM CLIENTE WHERE nome = 'Pedro Ferreira Lima';

INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '45678-9', '4567', '033', 'Banco Santander (Brasil) S.A.' FROM CLIENTE WHERE nome = 'Ana Carolina Souza';

INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '56789-0', '5678', '104', 'Caixa Econômica Federal' FROM CLIENTE WHERE nome = 'Carlos Eduardo Pereira';

INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '67890-1', '0001', '260', 'Nu Pagamentos S.A.' FROM CLIENTE WHERE nome = 'Fernanda Almeida';

INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '78901-2', '0001', '077', 'Banco Inter S.A.' FROM CLIENTE WHERE nome = 'Ricardo Barbosa';

INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '89012-3', '0001', '208', 'BTG Pactual S.A.' FROM CLIENTE WHERE nome = 'Juliana Rodrigues';
COMMIT;

-- 4) CONTAS INTERNAS (APENAS 1 POR CLIENTE)  [sem codigo_banco_externo]
INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0001234567', '1001', DATE '2023-01-15', 50000 FROM CLIENTE WHERE nome = 'João Silva Santos';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0002345678', '1001', DATE '2023-02-20', 50000 FROM CLIENTE WHERE nome = 'Maria Oliveira Costa';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0003456789', '1002', DATE '2023-03-10', 50000 FROM CLIENTE WHERE nome = 'Pedro Ferreira Lima';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0004567890', '1002', DATE '2023-04-05', 50000 FROM CLIENTE WHERE nome = 'Ana Carolina Souza';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0005678901', '1003', DATE '2023-05-12', 50000 FROM CLIENTE WHERE nome = 'Carlos Eduardo Pereira';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0006789012', '1003', DATE '2023-06-18', 50000 FROM CLIENTE WHERE nome = 'Fernanda Almeida';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0007890123', '1004', DATE '2023-07-22', 50000 FROM CLIENTE WHERE nome = 'Ricardo Barbosa';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
SELECT id_cliente, '0008901234', '1004', DATE '2023-08-30', 50000 FROM CLIENTE WHERE nome = 'Juliana Rodrigues';
COMMIT;

-- 5) CARTEIRAS (1:N)
-- 1ª carteira para cada conta interna
INSERT INTO CARTEIRA (conta_interna_id)
SELECT id_conta_interna FROM CONTA_INTERNA;

-- 2ª carteira para algumas contas (mantém 1:N sem duplicar CONTA_INTERNA)
INSERT INTO CARTEIRA (conta_interna_id)
SELECT id_conta_interna FROM CONTA_INTERNA WHERE numero_conta IN ('0001234567', '0002345678', '0003456789');
COMMIT;

-- 6) POSSE DE CRIPTOMOEDAS
-- João: BTC, ETH, ADA
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 0.5
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 2.45
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 1000
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ADA'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

-- Maria: BTC, MATIC, LINK
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 0.25
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 500
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'MATIC'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 25
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'LINK'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

-- Pedro: ETH, LTC, DOT
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 1.8
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 5
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'LTC'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 15
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'DOT'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

-- Ana: SOL, AVAX
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 10
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'SOL'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

-- Ana: AVAX
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 8.5
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'AVAX'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

-- Carlos: BTC, UNI
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 0.75
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 50
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'UNI'
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;
COMMIT;


-- Fernanda: ETH, ADA
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 3.2
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Fernanda Almeida' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 2000
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ADA'
WHERE cl.nome = 'Fernanda Almeida' AND ROWNUM = 1;

-- Ricardo: MATIC, LINK
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 800
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'MATIC'
WHERE cl.nome = 'Ricardo Barbosa' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 40
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'LINK'
WHERE cl.nome = 'Ricardo Barbosa' AND ROWNUM = 1;

-- Juliana: LTC, DOT
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 12
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'LTC'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 25
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'DOT'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;
COMMIT;

-- 7) TRANSACAO_FIAT (sem transação para Maria para manter apenas 2 no total)
INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 10000, 'DEPOSITO', TIMESTAMP '2023-01-15 09:00:00'
FROM CONTA_EXTERNA ce
JOIN CONTA_INTERNA ci ON ce.cliente_id_cliente = ci.cliente_id_cliente
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 8000, 'DEPOSITO', TIMESTAMP '2023-03-10 14:15:00'
FROM CONTA_EXTERNA ce
JOIN CONTA_INTERNA ci ON ce.cliente_id_cliente = ci.cliente_id_cliente
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 5000, 'SAQUE', TIMESTAMP '2023-06-15 16:45:00'
FROM CONTA_EXTERNA ce
JOIN CONTA_INTERNA ci ON ce.cliente_id_cliente = ci.cliente_id_cliente
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;

INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 3000, 'SAQUE', TIMESTAMP '2023-07-20 11:20:00'
FROM CONTA_EXTERNA ce
JOIN CONTA_INTERNA ci ON ce.cliente_id_cliente = ci.cliente_id_cliente
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
WHERE cl.nome = 'Fernanda Almeida' AND ROWNUM = 1;
COMMIT;

-- 8) TRANSACAO_CRIPTOMOEDA
-- João: compra BTC
INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 0.25, 45000, TIMESTAMP '2024-01-15 09:30:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

-- Maria: (1) compra ETH, (2) vende ETH  → total = 2 transações
INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 1.5, 2800, TIMESTAMP '2024-01-15 10:45:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'VENDA', 0.5, 2850, TIMESTAMP '2024-01-19 13:45:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

-- Pedro: compra ADA
INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 500, 0.45, TIMESTAMP '2024-01-16 11:20:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ADA'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

-- Ana: compra SOL
INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 5, 95.5, TIMESTAMP '2024-01-16 14:30:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'SOL'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

-- João: venda BTC
INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'VENDA', 0.1, 46000, TIMESTAMP '2024-01-18 16:20:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

-- Ricardo: compra LINK
INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 20, 12.5, TIMESTAMP '2024-01-22 12:25:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'LINK'
WHERE cl.nome = 'Ricardo Barbosa' AND ROWNUM = 1;

-- Juliana: compra LTC (FALHOU) e compra DOT (CONCLUIDA)
INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 8, 85, TIMESTAMP '2024-01-23 09:40:00', 'FALHOU'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'LTC'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 12, 6.25, TIMESTAMP '2024-01-24 14:55:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'DOT'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

-- Carlos: compra UNI (CANCELADA)
INSERT INTO TRANSACAO_CRIPTOMOEDA
  (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 25, 8.4, TIMESTAMP '2024-01-25 11:30:00', 'CANCELADA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'UNI'
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;
COMMIT;

-- ========================================
-- FIM DO SCRIPT DML
-- ========================================
