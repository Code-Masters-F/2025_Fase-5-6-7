-- ========================================
-- SCRIPT DML - POPULAÇÃO E MANIPULAÇÃO DE DADOS (COM COMMIT)
-- MODELO REFATORADO - SISTEMA CORRETORA DE CRIPTOMOEDAS
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
-- 2. INSERÇÃO DE CRIPTOMOEDAS
-- =========================
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

-- =========================
-- 3. INSERÇÃO DE CONTAS EXTERNAS
-- =========================
-- João Silva Santos - Conta no Banco do Brasil
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '12345-6', '1234', '001', 'Banco do Brasil S.A.'
FROM CLIENTE WHERE nome = 'João Silva Santos';

-- Maria Oliveira Costa - Conta no Itaú
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '23456-7', '2345', '341', 'Itaú Unibanco S.A.'
FROM CLIENTE WHERE nome = 'Maria Oliveira Costa';

-- Pedro Ferreira Lima - Conta no Bradesco
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '34567-8', '3456', '237', 'Banco Bradesco S.A.'
FROM CLIENTE WHERE nome = 'Pedro Ferreira Lima';

-- Ana Carolina Souza - Conta no Santander
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '45678-9', '4567', '033', 'Banco Santander (Brasil) S.A.'
FROM CLIENTE WHERE nome = 'Ana Carolina Souza';

-- Carlos Eduardo Pereira - Conta no Caixa
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '56789-0', '5678', '104', 'Caixa Econômica Federal'
FROM CLIENTE WHERE nome = 'Carlos Eduardo Pereira';

-- Fernanda Almeida - Conta no Nubank
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '67890-1', '0001', '260', 'Nu Pagamentos S.A.'
FROM CLIENTE WHERE nome = 'Fernanda Almeida';

-- Ricardo Barbosa - Conta no Inter
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '78901-2', '0001', '077', 'Banco Inter S.A.'
FROM CLIENTE WHERE nome = 'Ricardo Barbosa';

-- Juliana Rodrigues - Conta no BTG
INSERT INTO CONTA_EXTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
SELECT id_cliente, '89012-3', '0001', '208', 'BTG Pactual S.A.'
FROM CLIENTE WHERE nome = 'Juliana Rodrigues';

COMMIT;

-- =========================
-- 4. INSERÇÃO DE CONTAS INTERNAS
-- =========================
INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0001234567', '1001', '999', DATE '2023-01-15', 50000 FROM CLIENTE WHERE nome = 'João Silva Santos';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0002345678', '1001', '999', DATE '2023-02-20', 50000 FROM CLIENTE WHERE nome = 'Maria Oliveira Costa';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0003456789', '1002', '999', DATE '2023-03-10', 50000 FROM CLIENTE WHERE nome = 'Pedro Ferreira Lima';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0004567890', '1002', '999', DATE '2023-04-05', 50000 FROM CLIENTE WHERE nome = 'Ana Carolina Souza';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0005678901', '1003', '999', DATE '2023-05-12', 50000 FROM CLIENTE WHERE nome = 'Carlos Eduardo Pereira';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0006789012', '1003', '999', DATE '2023-06-18', 50000 FROM CLIENTE WHERE nome = 'Fernanda Almeida';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0007890123', '1004', '999', DATE '2023-07-22', 50000 FROM CLIENTE WHERE nome = 'Ricardo Barbosa';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0008901234', '1004', '999', DATE '2023-08-30', 50000 FROM CLIENTE WHERE nome = 'Juliana Rodrigues';

-- Contas adicionais para alguns clientes
INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0009012345', '1001', '999', DATE '2023-09-15', 50000 FROM CLIENTE WHERE nome = 'João Silva Santos';

INSERT INTO CONTA_INTERNA (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, data_abertura, saldo)
SELECT id_cliente, '0010123456', '1002', '999', DATE '2023-10-20', 50000 FROM CLIENTE WHERE nome = 'Maria Oliveira Costa';

COMMIT;

-- =========================
-- 5. INSERÇÃO DE CARTEIRAS (1:N - Uma conta interna pode ter várias carteiras)
-- =========================
-- Primeira carteira para cada conta
INSERT INTO CARTEIRA (conta_interna_id)
SELECT id_conta_interna FROM CONTA_INTERNA;

-- Segunda carteira para algumas contas (demonstrando relacionamento 1:N)
INSERT INTO CARTEIRA (conta_interna_id)
SELECT id_conta_interna FROM CONTA_INTERNA WHERE numero_conta IN ('0001234567', '0002345678', '0003456789');

COMMIT;

-- =========================
-- 6. INSERÇÃO DE POSSES DE CRIPTOMOEDAS
-- =========================

-- João Silva Santos - BTC, ETH, ADA
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 0.5
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM=1;

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

-- Maria Oliveira Costa - BTC, MATIC, LINK
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

-- Pedro Ferreira Lima - ETH, LTC, DOT
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

-- Ana Carolina Souza - SOL, AVAX
INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 10
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'SOL'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

INSERT INTO POSSE (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
SELECT ca.id_carteira, cr.id_criptomoeda, 8.5
FROM CARTEIRA ca
JOIN CONTA_INTERNA ci ON ca.conta_interna_id = ci.id_conta_interna
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'AVAX'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

-- Carlos Eduardo Pereira - BTC, UNI
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

-- Fernanda Almeida - ETH, ADA
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

-- Ricardo Barbosa - MATIC, LINK
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

-- Juliana Rodrigues - LTC, DOT
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

-- =========================
-- 7. INSERÇÃO DE TRANSACOES FIAT (DEPÓSITOS E SAQUES)
-- =========================

-- Depósitos iniciais (conta externa do cliente -> conta interna de outro cliente)
INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 10000, 'DEPOSITO', TIMESTAMP '2023-01-15 09:00:00'
FROM CONTA_EXTERNA ce
JOIN CLIENTE cl_ext ON ce.cliente_id_cliente = cl_ext.id_cliente
JOIN CONTA_INTERNA ci ON ci.cliente_id_cliente != cl_ext.id_cliente
JOIN CLIENTE cl_int ON ci.cliente_id_cliente = cl_int.id_cliente
WHERE cl_ext.nome = 'João Silva Santos' AND cl_int.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 15000, 'DEPOSITO', TIMESTAMP '2023-02-20 10:30:00'
FROM CONTA_EXTERNA ce
JOIN CLIENTE cl_ext ON ce.cliente_id_cliente = cl_ext.id_cliente
JOIN CONTA_INTERNA ci ON ci.cliente_id_cliente != cl_ext.id_cliente
JOIN CLIENTE cl_int ON ci.cliente_id_cliente = cl_int.id_cliente
WHERE cl_ext.nome = 'Maria Oliveira Costa' AND cl_int.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 8000, 'DEPOSITO', TIMESTAMP '2023-03-10 14:15:00'
FROM CONTA_EXTERNA ce
JOIN CLIENTE cl_ext ON ce.cliente_id_cliente = cl_ext.id_cliente
JOIN CONTA_INTERNA ci ON ci.cliente_id_cliente != cl_ext.id_cliente
JOIN CLIENTE cl_int ON ci.cliente_id_cliente = cl_int.id_cliente
WHERE cl_ext.nome = 'Pedro Ferreira Lima' AND cl_int.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

-- Saques (conta interna do cliente -> conta externa de outro cliente)
INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 5000, 'SAQUE', TIMESTAMP '2023-06-15 16:45:00'
FROM CONTA_EXTERNA ce
JOIN CLIENTE cl_ext ON ce.cliente_id_cliente = cl_ext.id_cliente
JOIN CONTA_INTERNA ci ON ci.cliente_id_cliente != cl_ext.id_cliente
JOIN CLIENTE cl_int ON ci.cliente_id_cliente = cl_int.id_cliente
WHERE cl_ext.nome = 'Carlos Eduardo Pereira' AND cl_int.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO TRANSACAO_FIAT (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
SELECT ce.id_conta_externa, ci.id_conta_interna, 3000, 'SAQUE', TIMESTAMP '2023-07-20 11:20:00'
FROM CONTA_EXTERNA ce
JOIN CLIENTE cl_ext ON ce.cliente_id_cliente = cl_ext.id_cliente
JOIN CONTA_INTERNA ci ON ci.cliente_id_cliente != cl_ext.id_cliente
JOIN CLIENTE cl_int ON ci.cliente_id_cliente = cl_int.id_cliente
WHERE cl_ext.nome = 'Fernanda Almeida' AND cl_int.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

COMMIT;

-- =========================
-- 8. INSERÇÃO DE TRANSACOES CRIPTOMOEDA
-- =========================

-- Compras de criptomoedas
INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 0.25, 45000, TIMESTAMP '2024-01-15 09:30:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 1.5, 2800, TIMESTAMP '2024-01-15 10:45:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 500, 0.45, TIMESTAMP '2024-01-16 11:20:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ADA'
WHERE cl.nome = 'Pedro Ferreira Lima' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 5, 95.5, TIMESTAMP '2024-01-16 14:30:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'SOL'
WHERE cl.nome = 'Ana Carolina Souza' AND ROWNUM = 1;

-- Vendas de criptomoedas
INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'VENDA', 0.1, 46000, TIMESTAMP '2024-01-18 16:20:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'BTC'
WHERE cl.nome = 'João Silva Santos' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'VENDA', 0.5, 2850, TIMESTAMP '2024-01-19 13:45:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'ETH'
WHERE cl.nome = 'Maria Oliveira Costa' AND ROWNUM = 1;

-- Transações com diferentes status
INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 300, 0.85, TIMESTAMP '2024-01-21 15:10:00', 'PENDENTE'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'MATIC'
WHERE cl.nome = 'Fernanda Almeida' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 20, 12.5, TIMESTAMP '2024-01-22 12:25:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'LINK'
WHERE cl.nome = 'Ricardo Barbosa' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 8, 85, TIMESTAMP '2024-01-23 09:40:00', 'FALHOU'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'LTC'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 12, 6.25, TIMESTAMP '2024-01-24 14:55:00', 'CONCLUIDA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'DOT'
WHERE cl.nome = 'Juliana Rodrigues' AND ROWNUM = 1;

INSERT INTO TRANSACAO_CRIPTOMOEDA (conta_interna_id, criptomoeda_id_criptomoeda, tipo_operacao, quantidade_criptomoeda, valor_unitario, data_hora, status)
SELECT ci.id_conta_interna, cr.id_criptomoeda, 'COMPRA', 25, 8.4, TIMESTAMP '2024-01-25 11:30:00', 'CANCELADA'
FROM CONTA_INTERNA ci
JOIN CLIENTE cl ON ci.cliente_id_cliente = cl.id_cliente
JOIN CRIPTOMOEDA cr ON cr.sigla = 'UNI'
WHERE cl.nome = 'Carlos Eduardo Pereira' AND ROWNUM = 1;

COMMIT;

-- ========================================
-- FIM DO SCRIPT DML
-- ========================================