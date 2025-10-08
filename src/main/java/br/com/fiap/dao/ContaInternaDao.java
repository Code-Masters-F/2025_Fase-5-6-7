package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Cliente;
import br.com.fiap.model.ContaInterna;

import java.math.BigDecimal;
import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaInternaDao {

    public ContaInternaDao(){
    }

    public int inserirContaInterna(int idCliente, int numeroConta, int agencia) throws SQLException {
        String sql = """
                INSERT INTO conta_interna (cliente_id_cliente, numero_conta, agencia, data_abertura, saldo)
                VALUES (?, ?, ?, ?, 0)
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[] {"id_conta"})) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, numeroConta);
            stmt.setInt(3, agencia);
            stmt.setDate(4, Date.valueOf(LocalDate.now()));

            int linhas = stmt.executeUpdate();
            if (linhas != 1)
                throw new SQLException("Falha ao inserir conta.");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Object key = rs.getObject(1);
                    if (key instanceof BigDecimal bd) {
                        return bd.intValue();
                    }
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Não foi possível obter o id gerado da conta.");
                }
            }

        }
    }

    public void atualizarSaldo(int numeroConta, int agencia, double valor) throws SQLException {
        String sql = "UPDATE conta_interna SET saldo = saldo + ? WHERE numero_conta = ? AND agencia = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, valor);
            stmt.setInt(2, numeroConta);
            stmt.setInt(3, agencia);

            int linhas = stmt.executeUpdate();

            if (linhas == 0) {
                throw new SQLException("Conta não encontrada para atualização de saldo.");
            }
        }

    }

    public ContaInterna buscarContaPorCliente(Cliente cliente) throws SQLException {
        final String sql = """
                SELECT *
                FROM conta
                WHERE cliente_id_cliente = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id_conta = rs.getInt("id_conta");
                    int numero_conta = rs.getInt("numero_conta");
                    int agencia = rs.getInt("agencia");
                    double saldo = rs.getDouble("saldo");

                    CarteiraDao daoCarteira = new CarteiraDao();
                    int id_carteira = daoCarteira.buscarCarteiraPorContaId(id_conta);

                    return new ContaInterna(id_conta, numero_conta, agencia, saldo, id_carteira, cliente);
                }
            }
        }
        return null;
    }

    public List<ContaInterna> buscarContasPorClienteId(int idCliente) throws SQLException {

        List<ContaInterna> contas = new ArrayList<>();

        final String sql = """
                SELECT id_conta, cliente_id_cliente, numero_conta, agencia, saldo
                FROM conta
                WHERE cliente_id_cliente = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ContaInterna conta = new ContaInterna();
                    conta.setId(rs.getInt("id_conta"));

                    Cliente cli = new Cliente();
                    cli.setId(rs.getInt("cliente_id_cliente"));

                    conta.setCliente(cli);
                    conta.setNumeroConta(rs.getInt("numero_conta"));
                    conta.setNumeroAgencia(rs.getInt("agencia"));
                    conta.setSaldo(rs.getDouble("saldo"));

                    contas.add(conta);
                }
            }
        }
        return contas;
    }

    public void transferirParaContaInterna(int idOrigem, int idDestino, double valor) throws SQLException {
        if (idOrigem == idDestino) throw new SQLException("Origem e destino são a mesma conta.");
        if (valor <= 0) throw new SQLException("Valor inválido");

        final String sqlLockSaldo = "SELECT saldo FROM conta WHERE id_conta = ? FOR UPDATE";
        final String sqlDebito = "UPDATE conta SET saldo = saldo - ? WHERE id_conta = ?";
        final String sqlCredito = "UPDATE conta SET saldo = saldo + ? WHERE id_conta = ?";

        Connection conexao = null;
        try {
            conexao = ConnectionFactory.getConnection();
            conexao.setAutoCommit(false);

            try (PreparedStatement psLock = conexao.prepareStatement(sqlLockSaldo)) {
                psLock.setInt(1, idOrigem);

                try (ResultSet rs = psLock.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Conta de origem não encontrada.");
                    double saldoOrigem = rs.getDouble(1);

                    if (saldoOrigem < valor) throw new SQLException("Saldo insuficiente na conta de origem.");
                }

                psLock.setInt(1, idDestino);
                try (ResultSet rs = psLock.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Conta de destino não encontrada.");
                }
            }


            try (PreparedStatement deb = conexao.prepareStatement(sqlDebito);
                 PreparedStatement cre = conexao.prepareStatement(sqlCredito)) {
                deb.setDouble(1, valor);
                deb.setInt(2, idOrigem);
                if (deb.executeUpdate() != 1) throw new SQLException("Falha ao debitar origem.");

                cre.setDouble(1, valor);
                cre.setInt(2, idDestino);
                if (cre.executeUpdate() != 1) throw new SQLException("Falha ao creditar destino.");
            }
            conexao.commit();
        } catch (SQLException e) {
            if (conexao != null) {
                try { conexao.rollback(); } catch (SQLException ex) {
                    System.err.println("Erro no rollback: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } finally {
            if (conexao != null) {
                try { conexao.close(); } catch (SQLException ex) {
                    System.err.println("Erro ao fechar conexão: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    public List<ContaInterna> listarContasPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT id_conta, numero_conta, agencia, saldo " +
                "FROM CONTA WHERE CLIENTE_ID_cliente = ? ORDER BY id_conta";

        List<ContaInterna> contas = new ArrayList<>();
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ContaInterna conta = new ContaInterna();
                    conta.setId(rs.getInt("id_conta"));
                    conta.setNumeroConta(rs.getInt("numero_conta"));
                    conta.setNumeroAgencia(rs.getInt("agencia"));
                    conta.setSaldo(rs.getDouble("saldo"));

                    contas.add(conta);
                }
            }
        }
        return contas;
    }
}
