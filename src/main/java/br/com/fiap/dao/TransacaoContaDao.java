package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.TransacaoConta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoContaDao {

    public TransacaoContaDao() {
    }

    public void inserirTransacaoConta(TransacaoConta transacao) throws SQLException {
        final String SQL_LOOKUP_CONTA_INTERNA = "SELECT id_conta_interna FROM conta_interna WHERE numero_conta = ? AND agencia = ?";
        final String SQL_LOOKUP_CONTA_EXTERNA = "SELECT id_conta_externa FROM conta_externa WHERE numero_conta = ? AND agencia = ?";
        final String SQL_INSERT = """
                INSERT INTO transacao_fiat (conta_externa_id, conta_interna_id, valor, tipo, data_hora)
                VALUES (?, ?, ?, ?, ?)
                """;

        if (transacao.getTipo() == null) {
            throw new SQLException("Tipo da transação não informado (DEPOSITO/SAQUE).");
        }
        final String tipoStr = transacao.getTipo().name();

        final java.sql.Timestamp dataHora = java.sql.Timestamp.valueOf(
                transacao.getDataHora() != null ? transacao.getDataHora() : java.time.LocalDateTime.now()
        );

        try (Connection cx = ConnectionFactory.getConnection()) {
            cx.setAutoCommit(false);

            Integer idContaInterna = null;
            Integer idContaExterna = null;

            try (PreparedStatement stmt = cx.prepareStatement(SQL_LOOKUP_CONTA_INTERNA)) {
                stmt.setInt(1, transacao.getNumeroContaInterna());
                stmt.setInt(2, transacao.getAgenciaContaInterna());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) idContaInterna = rs.getInt(1);
                }
            }

            if (idContaInterna == null) {
                cx.rollback();
                throw new SQLException("Conta interna não encontrada (número/agência).");
            }

            try (PreparedStatement stmt = cx.prepareStatement(SQL_LOOKUP_CONTA_EXTERNA)) {
                stmt.setInt(1, transacao.getNumeroContaExterna());
                stmt.setInt(2, transacao.getAgenciaContaExterna());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) idContaExterna = rs.getInt(1);
                }
            }
            if (idContaExterna == null) {
                cx.rollback();
                throw new SQLException("Conta externa não encontrada (número/agência).");
            }

            try (PreparedStatement stmt = cx.prepareStatement(SQL_INSERT)) {
                stmt.setInt(1, idContaExterna);
                stmt.setInt(2, idContaInterna);
                stmt.setDouble(3, transacao.getValor());
                stmt.setString(4, tipoStr);
                stmt.setTimestamp(5, dataHora);
                int rows = stmt.executeUpdate();
                if (rows != 1) {
                    cx.rollback();
                    throw new SQLException("Falha ao inserir transação FIAT.");
                }
            }
            cx.commit();
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
