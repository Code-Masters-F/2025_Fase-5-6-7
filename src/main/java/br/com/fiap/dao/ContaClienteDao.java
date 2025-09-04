package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.ContaCliente;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContaClienteDao {
    Connection conexao;

    public ContaClienteDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserirConta(int idCliente, int numeroConta, int agencia) throws SQLException {
        String sql = """
                INSERT INTO conta (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
                VALUES (?, ?, ?, ?, 0)
                """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, numeroConta);
            stmt.setInt(3, agencia);
            stmt.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));

            int linhas = stmt.executeUpdate();
            if (linhas != 1) {
                throw new SQLException("Falha ao inserir conta.");
            }
        }
    }

    public void atualizarSaldo(int numeroConta, int agencia, double valor) throws SQLException {
        String sql = "UPDATE conta SET saldo = saldo + ? WHERE numero_conta = ? AND agencia = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDouble(1, valor);
            stmt.setInt(2, numeroConta);
            stmt.setInt(3, agencia);

            int linhas = stmt.executeUpdate();

            if (linhas == 0) {
                throw new SQLException("Conta não encontrada para atualização de saldo.");
            }
        }

    }

    public ContaCliente buscarContaPorClienteId(int idCliente) throws SQLException {
        final String sql = """
                SELECT id_conta, cliente_id_cliente, numero_conta, agencia, saldo
                FROM conta
                WHERE cliente_id_cliente = ?
                """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ContaCliente conta = new ContaCliente();
                    conta.setId(rs.getInt("id_conta"));
                    conta.getCliente().setId(rs.getInt("cliente_id"));
                    conta.setNumeroConta(rs.getInt("numero_conta"));
                    conta.setNumeroAgencia(rs.getInt("agencia"));
                    conta.setSaldo(rs.getDouble("saldo"));
                    return conta;
                }
            }
        }
        return null;
    }

    public void transferirParaContaInterna(int idOrigem, int idDestino, double valor) throws SQLException {
        if (idOrigem == idDestino) throw new SQLException("Origem e destino são a mesma conta.");
        if (valor <= 0) throw new SQLException("Valor inválido");

        final String sqlLockSaldo = "SELECT saldo FROM conta WHERE id_conta = ? FOR UPDATE";
        final String sqlDebito = "UPDATE conta SET saldo = saldo - ? WHERE id_conta = ?";
        final String sqlCredito = "UPDATE conta SET saldo = saldo + ? WHERE id_conta = ?";

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

    public List<ContaCliente> listarContasPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT id_conta, numero_conta, agencia, saldo " +
                "FROM CONTA WHERE CLIENTE_ID_cliente = ? ORDER BY id_conta";

        List<ContaCliente> contas = new ArrayList<>();
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ContaCliente conta = new ContaCliente();
                    conta.setId(rs.getInt("id_conta"));
                    conta.setNumeroAgencia(rs.getInt("numero_conta"));
                    conta.setNumeroAgencia(rs.getInt("agencia"));
                    conta.setSaldo(rs.getDouble("saldo"));

                    contas.add(conta);
                }
            }
        }
        return contas;
    }

    //----HELPERS----








}
