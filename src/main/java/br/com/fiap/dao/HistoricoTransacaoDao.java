package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.HistoricoTransacao;
import br.com.fiap.model.TipoTransacaoFiat;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class HistoricoTransacaoDao {

    /** Consulta por id da conta, com período e paginação */
    public List<HistoricoTransacao> consultarHistoricoPorConta(
            long idConta, Instant from, Instant to, int page, int size) throws SQLException {

        final String sql = """
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

        var itens = new ArrayList<HistoricoTransacao>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;
            ps.setTimestamp(i++, Timestamp.from(from));
            ps.setTimestamp(i++, Timestamp.from(to));
            ps.setLong(i++, idConta);

            // 5x idConta
            ps.setLong(i++, idConta);
            ps.setLong(i++, idConta);
            ps.setLong(i++, idConta);
            ps.setLong(i++, idConta);

            int safeSize = Math.max(1, size);
            int safePage = Math.max(0, page);
            ps.setInt(i++, safePage * safeSize);
            ps.setInt(i,   safeSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TipoTransacaoFiat tipo = TipoTransacaoFiat.valueOf(rs.getString("tipo_movimento"));
                    Instant dataHora = rs.getTimestamp("data_hora").toInstant();
                    BigDecimal valor = rs.getBigDecimal("valor");

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

    /** Overload: consulta recebendo numero/agencia (resolve id_conta e reutiliza o método principal) */
    public List<HistoricoTransacao> consultarHistoricoPorNumeroAgencia(
            int numeroConta, int agencia, Instant from, Instant to, int page, int size) throws SQLException {

        long idConta = buscarIdConta(numeroConta, agencia);
        if (idConta <= 0) return List.of();
        return consultarHistoricoPorConta(idConta, from, to, page, size);
    }

    /** Busca id_conta por numero/agencia (útil p/ telas que recebem esses campos) */
    private long buscarIdConta(int numeroConta, int agencia) throws SQLException {
        final String sql = "SELECT id_conta FROM CONTA WHERE numero_conta = ? AND agencia = ?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, numeroConta);
            ps.setInt(2, agencia);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : -1L;
            }
        }
    }
}
