package br.com.fiap.dao;

public class HistoricoTransacaoDao {

    public List<TransacaoHistorico> consultarHistoricoPorConta(
            long idConta, Instant from, Instant to, int page, int size) throws SQLException {

        String sql = """
            WITH base AS (
              SELECT tf.id_transacao_fiat AS id_transacao,
                     tf.data_hora,
                     tf.valor,
                     o.id_conta AS id_origem,
                     d.id_conta AS id_destino,
                     co.nome    AS nome_origem,
                     cd.nome    AS nome_destino
              FROM TRANSACAO_FIAT tf
              JOIN CONTA o  ON o.id_conta = tf.CONTA_id_conta_origem
              JOIN CONTA d  ON d.id_conta = tf.CONTA_id_conta_destino
              JOIN CLIENTE co ON co.id_cliente = o.CLIENTE_id_cliente
              JOIN CLIENTE cd ON cd.id_cliente = d.CLIENTE_id_cliente
              WHERE tf.data_hora BETWEEN ? AND ?
                AND (? IN (tf.CONTA_id_conta_origem, tf.CONTA_id_conta_destino))
            )
            SELECT id_transacao, data_hora,
                   CASE WHEN id_origem = ? THEN 'DEBITO' ELSE 'CREDITO' END AS tipo_movimento,
                   CASE WHEN id_origem = ? THEN -valor ELSE  valor END AS valor,
                   CASE WHEN id_origem = ? THEN id_destino ELSE id_origem END AS id_conta_contraparte,
                   CASE WHEN id_origem = ? THEN nome_destino ELSE nome_origem END AS nome_contraparte
            FROM base
            ORDER BY data_hora DESC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """;
        public List<TransacaoHistorico> consultarHistoricoPorConta(
        long idConta, Instant from, Instant to, int page, int size) throws SQLException {

            String sql = """
                    WITH base AS (
                      SELECT tf.id_transacao_fiat AS id_transacao,
                             tf.data_hora,
                             tf.valor,
                             o.id_conta AS id_origem,
                             d.id_conta AS id_destino,
                             co.nome    AS nome_origem,
                             cd.nome    AS nome_destino
                      FROM TRANSACAO_FIAT tf
                      JOIN CONTA o  ON o.id_conta = tf.CONTA_id_conta_origem
                      JOIN CONTA d  ON d.id_conta = tf.CONTA_id_conta_destino
                      JOIN CLIENTE co ON co.id_cliente = o.CLIENTE_id_cliente
                      JOIN CLIENTE cd ON cd.id_cliente = d.CLIENTE_id_cliente
                      WHERE tf.data_hora BETWEEN ? AND ?
                        AND (? IN (tf.CONTA_id_conta_origem, tf.CONTA_id_conta_destino))
                    )
                    SELECT id_transacao, data_hora,
                           CASE WHEN id_origem = ? THEN 'DEBITO' ELSE 'CREDITO' END AS tipo_movimento,
                           CASE WHEN id_origem = ? THEN -valor ELSE  valor END AS valor,
                           CASE WHEN id_origem = ? THEN id_destino ELSE id_origem END AS id_conta_contraparte,
                           CASE WHEN id_origem = ? THEN nome_destino ELSE nome_origem END AS nome_contraparte
                    FROM base
                    ORDER BY data_hora DESC
                    OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                    """;

        }
    }
