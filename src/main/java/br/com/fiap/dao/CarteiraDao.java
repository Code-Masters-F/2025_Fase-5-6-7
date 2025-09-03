package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Carteira;
import br.com.fiap.model.PosseClienteCrypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarteiraDao {
    Connection conexao;

    public CarteiraDao() throws SQLException  {
        conexao = ConnectionFactory.getConnection();
    }

    public Integer buscarCarteiraPorContaId(int idConta) throws SQLException {
        final String sql = """
                SELECT carteira_id_carteira
                FROM conta
                WHERE id_conta = ?
                """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("carteira_id_carteira") : null;
            }
        }
    }








}
