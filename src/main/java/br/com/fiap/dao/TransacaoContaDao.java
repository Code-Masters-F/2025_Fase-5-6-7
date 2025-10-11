package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.TransacaoConta;

import java.math.BigDecimal;
import java.sql.*;

public class TransacaoContaDao {

    public TransacaoContaDao() {
    }

    public void inserirTransacaoConta(Connection cx, TransacaoConta transacao) throws SQLException {
        final String SQL = """
                INSERT INTO transacao_fiat (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = cx.prepareStatement(SQL)) {
            stmt.setInt(1, transacao.getContaExterna().getId());
            stmt.setInt(2, transacao.getContaInterna().getId());
            stmt.setBigDecimal(3, transacao.getValor());
            stmt.setString(4, transacao.getTipo().name());
            stmt.setTimestamp(5, Timestamp.valueOf(transacao.getDataHora()));

            if (stmt.executeUpdate() != 1) throw new SQLException("Falha ao inserir transação.");

        }
    }

    public int buscarContaInterna(int numeroConta, int numeroAgencia) throws SQLException {
        String sql = """
                SELECT * FROM conta_interna
                WHERE numero_conta = ?
                    AND agencia = ?;
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, numeroConta);
            stmt.setInt(2, numeroAgencia);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_conta_interna");
                }
                return -1;
            }

        }
    }
}
