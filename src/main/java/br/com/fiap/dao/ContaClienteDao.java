package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaClienteDao {
    Connection conexao;


    public ContaClienteDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void transferirParaContaExterna(int idOrigem, int idDestino, double valor) throws SQLException {

        if (idOrigem == idDestino) throw new SQLException("Origem e destino são a mesma conta.");
        if (valor <= 0) throw new SQLException("Valor inválido");

        final String sqlLockSaldo = "SELECT saldo FROM conta WHERE id_conta = ? FOR UPDATE";
        final String sqlDebito = "UPDATE conta SET saldo = saldo - ? WHERE id_conta = ?";
        final String sqlCredito = "UPDATE conta SET saldo = saldo + ? WHERE id_conta = ?";
        final String sqlInsertTransacao = "INSERT INTO transacao_fiat (conta_id_conta_origem, conta_id_conta_destino, valor) VALUES (?, ?, ?)";

        try {
            double saldoOrigem;

            try (PreparedStatement stmt = conexao.prepareStatement(sqlLockSaldo)) {
                stmt.setInt(1, idOrigem);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Conta de origem não encontrada.");
                    saldoOrigem = rs.getDouble(1);
                }
            }

            if (saldoOrigem < valor) throw new SQLException("Saldo insuficiente na conta de origem.");

            try (PreparedStatement stmt = conexao.prepareStatement(sqlLockSaldo)) {
                stmt.setInt(1, idDestino);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Conta de destino não encontrada.");
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sqlDebito)) {
                stmt.setDouble(1, valor);
                stmt.setInt(2, idOrigem);
                if (stmt.executeUpdate() != 1) throw new SQLException("Falha ao debitar origem.");
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sqlCredito)) {
                stmt.setDouble(1, valor);
                stmt.setInt(2, idDestino);
                if (stmt.executeUpdate() != 1) throw new SQLException("Falha ao creditar destino.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }






    }





}
