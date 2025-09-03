package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.StatusOperacao;
import br.com.fiap.model.TransacaoCrypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransacaoCryptoDao {
    Connection conexao;

    public TransacaoCryptoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserirTransacaoCrypto(int contaId, int cryptoId,
                                       String tipoOperacao,
                                       double quantidade, double valorUnitario,
                                       StatusOperacao status) throws SQLException {
        String sql = """
        INSERT INTO transacao_crypto
            (conta_id_conta, crypto_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, status)\s
        VALUES (?, ?, ?, ?, ?, ?)
       """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, contaId);
            stmt.setInt(2, cryptoId);
            stmt.setString(3, tipoOperacao);
            stmt.setDouble(4, quantidade);
            stmt.setDouble(5, valorUnitario);
            stmt.setString(6, String.valueOf(status));
            stmt.executeUpdate();
        }
    }




}
