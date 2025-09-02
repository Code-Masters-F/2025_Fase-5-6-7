package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Conta;
import br.com.fiap.model.TransacaoConta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoContaDao {
    Connection conexao;

    public TransacaoContaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserirTransacaoConta(TransacaoConta transacao) throws SQLException {
        String sqlLookupConta = "SELECT id_conta FROM conta WHERE numero_conta = ? AND agencia = ?";
        String sqlInsertTransacao = "INSERT INTO transacao_fiat (conta_id_conta_origem, conta_id_conta_destino, valor) VALUES (?, ?, ?)";

        try (PreparedStatement psOrigem = conexao.prepareStatement(sqlLookupConta);
             PreparedStatement psDestino = conexao.prepareStatement(sqlLookupConta);
             PreparedStatement psInsert = conexao.prepareStatement(sqlInsertTransacao)) {

            psOrigem.setInt(1, transacao.getNumeroContaOrigem());
            psOrigem.setInt(2, transacao.getAgenciaOrigem());

            Integer idOrigem = null;

            try (ResultSet rs = psOrigem.executeQuery()) {
                if (rs.next()) {
                    idOrigem = rs.getInt(1);
                }
            }

            if (idOrigem == null) {
                throw new SQLException("Conta de origem não encontrada (número/agência).");
            }

            psDestino.setInt(1, transacao.getNumeroContaDestino());
            psDestino.setInt(2, transacao.getAgenciaDestino());

            Integer idDestino = null;

            try (ResultSet rs = psDestino.executeQuery()) {
                if (rs.next()) {
                    idDestino = rs.getInt(1);
                }
            }

            if (idDestino == null) {
                throw new SQLException("Conta de destino não encontrada (número/agência).");
            }

            psInsert.setInt(1, idOrigem);
            psInsert.setInt(2, idDestino);
            psInsert.setDouble(3, transacao.getValor());
            psInsert.executeUpdate();

        }
    }

    public int buscarConta(int numeroConta, int numeroAgencia) throws SQLException {
        String sql = """
                SELECT * FROM conta
                WHERE numero_conta = ?
                    AND agencia = ?;
                """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, numeroConta);
            stmt.setInt(2, numeroAgencia);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_conta");
                }
                return -1;
            }

        }
    }
}
