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
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[] {"id_conta_interna"})) {

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

    public void debitarSaldo(Connection cx, int idConta, BigDecimal valor) throws SQLException {
        try (PreparedStatement stmt = cx.prepareStatement(
                "SELECT saldo FROM conta_interna WHERE id_conta_externa = ? FOR UPDATE")) {
            stmt.setInt(1, idConta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) throw new SQLException("Conta interna não encontrada.");
                BigDecimal saldo = rs.getBigDecimal(1);
                if (saldo == null) saldo = BigDecimal.ZERO;

                if (saldo.compareTo(valor) < 0) {
                    throw new SQLException("Saldo insuficiente.");
                }
            }
        }

        try (PreparedStatement stmt = cx.prepareStatement(
                "UPDATE conta_interna SET saldo = saldo - ? WHERE id_conta_interna = ?")) {
            stmt.setBigDecimal(1, valor);
            stmt.setInt(2, idConta);
            if (stmt.executeUpdate() != 1) {
                throw new SQLException("Falha ao debitar saldo.");
            }
        }
    }

    public ContaInterna buscarContaInternaPorCliente(Cliente cliente) throws SQLException {
        final String SQL = """
                SELECT *
                FROM conta_interna
                WHERE cliente_id_cliente = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SQL)) {

            stmt.setInt(1, cliente.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id_conta = rs.getInt("id_conta_interna");
                    String numero_conta = rs.getString("numero_conta");
                    String agencia = rs.getString("agencia");
                    double saldo = rs.getDouble("saldo");

                    CarteiraDao daoCarteira = new CarteiraDao();
                    int id_carteira = daoCarteira.buscarCarteiraPorContaId(id_conta);

                    return new ContaInterna(id_conta, numero_conta, agencia, saldo, id_carteira, cliente);
                }
            }
        }
        return null;
    }

    public static ContaInterna buscarContaInternaPorNumeroEAgencia(String numeroConta, String agencia) throws SQLException {
        final String SQL = "SELECT * FROM conta_interna WHERE numero_conta = ? AND agencia = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SQL)) {

            stmt.setString(1, numeroConta);
            stmt.setString(2, agencia);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    ContaInterna contaInterna = new ContaInterna();
                    contaInterna.setId(rs.getInt("id_conta_interna"));
                    contaInterna.setNumeroConta(rs.getString("numero_conta"));
                    contaInterna.setNumeroAgencia(rs.getString("agencia"));
                    contaInterna.setSaldo(rs.getDouble("saldo"));

                    return contaInterna;
                }
            }
        }

        return null;
    }

    public List<ContaInterna> buscarContasPorClienteId(int idCliente) throws SQLException {

        List<ContaInterna> contas = new ArrayList<>();

        final String sql = """
                SELECT id_conta_interna, cliente_id_cliente, numero_conta, agencia, saldo
                FROM conta_interna
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
                    conta.setNumeroConta(rs.getString("numero_conta"));
                    conta.setNumeroAgencia(rs.getString("agencia"));
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

        final String sqlLockSaldo = "SELECT saldo FROM conta_interna WHERE id_conta_interna = ? FOR UPDATE";
        final String sqlDebito = "UPDATE conta_interna SET saldo = saldo - ? WHERE id_conta_interna = ?";
        final String sqlCredito = "UPDATE conta_interna SET saldo = saldo + ? WHERE id_conta_interna = ?";

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

    public List<ContaInterna> listarContasInternasPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT id_conta_interna, numero_conta, agencia, saldo " +
                "FROM conta_interna WHERE CLIENTE_ID_cliente = ? ORDER BY id_conta_interna";

        List<ContaInterna> contas = new ArrayList<>();
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ContaInterna conta = new ContaInterna();
                    conta.setId(rs.getInt("id_conta"));
                    conta.setNumeroConta(rs.getString("numero_conta"));
                    conta.setNumeroAgencia(rs.getString("agencia"));
                    conta.setSaldo(rs.getDouble("saldo"));

                    contas.add(conta);
                }
            }
        }
        return contas;
    }
}
