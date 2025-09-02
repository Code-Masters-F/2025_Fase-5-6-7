package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.TransacaoConta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransacaoContaDao {
    Connection conexao;

    public TransacaoContaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserirTransacaoConta(TransacaoConta transacao) throws SQLException {
        String sql = "INSERT INTO transacao_fiat (conta_id_conta_origem, conta_id_conta_destino, valor) VALUES (?, ?, ?)";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, transacao.getNumeroContaOrigem());
        stmt.setInt(2, transacao.getNumeroContaDestino());
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
