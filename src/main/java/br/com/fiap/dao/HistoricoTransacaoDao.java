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

    /**
     * Consulta o histórico de transações de uma conta interna
     *
     * @param idContaInterna ID da conta interna a consultar
     * @param from Data/hora inicial do período
     * @param to Data/hora final do período
     * @param page Número da página (começa em 0)
     * @param size Quantidade de registros por página
     * @return Lista de transações ordenadas da mais recente para a mais antiga
     */
    public List<HistoricoTransacao> consultarHistoricoPorConta(
            long idContaInterna, Instant from, Instant to, int page, int size) throws SQLException {

        String sql = """
            SELECT tf.id_transacao,
                   tf.data_hora,
                   tf.valor,
                   tf.tipo,
                   COALESCE(ce.id_conta_externa, 0) AS id_conta_contraparte,
                   COALESCE(cle.nome, 'Sistema') AS nome_contraparte
            FROM TRANSACAO_FIAT tf
            JOIN CONTA_INTERNA ci ON ci.id_conta_interna = tf.CONTA_INTERNA_ID
            LEFT JOIN CONTA_EXTERNA ce ON ce.id_conta_externa = tf.CONTA_EXTERNA_ID
            JOIN CLIENTE cli ON cli.id_cliente = ci.CLIENTE_ID_CLIENTE
            LEFT JOIN CLIENTE cle ON cle.id_cliente = ce.CLIENTE_ID_CLIENTE
            WHERE tf.data_hora BETWEEN ? AND ?
              AND ci.id_conta_interna = ?
            ORDER BY tf.data_hora DESC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """;

        var itens = new ArrayList<HistoricoTransacao>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Define os parâmetros
            int i = 1;
            ps.setTimestamp(i++, Timestamp.from(from));
            ps.setTimestamp(i++, Timestamp.from(to));
            ps.setLong(i++, idContaInterna);

            // Paginação
            int safeSize = Math.max(1, size);
            int safePage = Math.max(0, page);
            ps.setInt(i++, safePage * safeSize);
            ps.setInt(i, safeSize);

            // Executa e processa resultados
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tipoStr = rs.getString("tipo");
                    TipoTransacaoFiat tipo = TipoTransacaoFiat.valueOf(tipoStr);
                    Instant dataHora = rs.getTimestamp("data_hora").toInstant();
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