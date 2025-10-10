package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.ContaExterna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaExternaDao {

    public void inserirContaExterna(int idCliente, int numeroConta, int agencia, int codigoBancoExterno, String nomeBanco) throws SQLException {
        final String SQL = """
                INSERT INTO conta_externa (cliente_id_cliente, numero_conta, agencia, codigo_banco_externo, nome_banco)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SQL)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, numeroConta);
            stmt.setInt(3, agencia);
            stmt.setInt(4, codigoBancoExterno);
            stmt.setString(5, nomeBanco);

            int rows = stmt.executeUpdate();
            if (rows != 1) throw new SQLException("Falha ao inserir Conta Externa!");
        }
    }

    public static ContaExterna buscarContaExternaPorNumeroEAgencia(String numeroConta, String agencia) throws SQLException {
        final String SQL = "SELECT * FROM conta_externa WHERE numero_conta = ? AND agencia = ?";

        try(Connection conexao = ConnectionFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(SQL)) {

            stmt.setString(1, numeroConta);
            stmt.setString(2, agencia);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ContaExterna contaExterna = new ContaExterna();
                    contaExterna.setId(rs.getInt("id_conta_externa"));
                    contaExterna.setNumeroConta(rs.getString("numero_conta"));
                    contaExterna.setNumeroAgencia(rs.getString("agencia"));

                    return contaExterna;
                }
            }
        }
        return null;
    }

    public void enviarTransferenciaContaExternaParaInterna() {

    }




}
