package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.HistoricoTransacao;
import br.com.fiap.model.TipoTransacaoFiat;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para consulta de histórico de transações FIAT
 */
public class HistoricoTransacaoDao {

    private static final String SCHEMA = "RM557577"; // ⚠ ALTERAR PARA SEU RM

    /**
     * Consulta o histórico de transações de uma conta
     *
     * @param idConta ID da conta a consultar
     * @param from Data/hora inicial do período
     * @param to Data/hora final do período
     * @param page Número da página (começa em 0)
     * @param size Quantidade de registros por página
     * @return Lista de transações ordenadas da mais recente para a mais antiga
     */
    public List<HistoricoTransacao> consultarHistoricoPorConta(
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
              FROM %s.TRANSACAO_FIAT tf
              JOIN %s.CONTA o  ON o.id_conta = tf.CONTA_id_conta_origem
              JOIN %s.CONTA d  ON d.id_conta = tf.CONTA_id_conta_destino
              JOIN %s.CLIENTE co ON co.id_cliente = o.CLIENTE_id_cliente
              JOIN %s.CLIENTE cd ON cd.id_cliente = d.CLIENTE_id_cliente
              WHERE tf.data_hora BETWEEN ? AND ?
                AND ? IN (tf.CONTA_id_conta_origem, tf.CONTA_id_conta_destino)
            )
            SELECT id_transacao, data_hora,
                   CASE WHEN id_origem = ? THEN 'DEBITO' ELSE 'CREDITO' END AS tipo_movimento,
                   CASE WHEN id_origem = ? THEN -valor ELSE valor END AS valor,
                   CASE WHEN id_origem = ? THEN id_destino ELSE id_origem END AS id_conta_contraparte,
                   CASE WHEN id_origem = ? THEN nome_destino ELSE nome_origem END AS nome_contraparte
            FROM base
            ORDER BY data_hora DESC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """.formatted(SCHEMA, SCHEMA, SCHEMA, SCHEMA, SCHEMA);

        var itens = new ArrayList<HistoricoTransacao>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Define os parâmetros
            int i = 1;
            ps.setTimestamp(i++, Timestamp.from(from));
            ps.setTimestamp(i++, Timestamp.from(to));
            ps.setLong(i++, idConta);
            ps.setLong(i++, idConta);
            ps.setLong(i++, idConta);
            ps.setLong(i++, idConta);
            ps.setLong(i++, idConta);

            // Paginação
            int safeSize = Math.max(1, size);
            int safePage = Math.max(0, page);
            ps.setInt(i++, safePage * safeSize);
            ps.setInt(i, safeSize);

            // Executa e processa resultados
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    var tipo = TipoTransacaoFiat.valueOf(rs.getString("tipo_movimento"));
                    var dataHora = rs.getTimestamp("data_hora").toInstant();
                    var valor = rs.getBigDecimal("valor");

                    itens.add(new HistoricoTransacao(
                            rs.getLong("id_transacao"),
                            dataHora,
                            tipo,
                            valor,
                            rs.getLong("id_conta_contraparte"),
                            rs.getString("nome_contraparte")
                    ));
                }
            }
        }
        return itens;
    }
}