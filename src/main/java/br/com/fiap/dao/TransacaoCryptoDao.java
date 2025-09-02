package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.TransacaoCrypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransacaoCryptoDao {
    Connection conexao;

    public TransacaoCryptoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserirTransacaoCrypto(TransacaoCrypto transacao) throws SQLException {
        String sql = "INSERT INTO transacao_crypto (conta_id_conta, crypto_id_crypto, tipo_operacao, quantidade_crypto, valor_unitario, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, transacao.getContaCliente().getId());
        stmt.setInt(2, transacao.getCrypto().getId());
        stmt.setString(3, transacao.getTipoOperacao());
        stmt.setDouble(4, transacao.getQuantidadeCrypto());
        stmt.setDouble(5, transacao.getValorUnitarioCrypto());
        stmt.setString(6, String.valueOf(transacao.getStatus()));

        stmt.executeUpdate();
        fecharConexao();
    }

    private void fecharConexao() throws SQLException {
        conexao.close();
    }


}
