package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.utils.CryptoPriceSimulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CarteiraDao {

    public CarteiraDao() {
    }

    public void inserirCarteira(int idConta) throws SQLException {
        String sql = """
                INSERT INTO carteira (conta_interna_id)
                VALUES (?)
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idConta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Falha ao inserir a carteira do cliente: " + e.getMessage());
        }

    }

    public Integer buscarCarteiraPorContaId(int idConta) throws SQLException {
        final String sql = """
                SELECT id_carteira
                FROM carteira
                WHERE conta_interna_id = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idConta);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("id_carteira") : null;
            }
        }
    }

    public record CompraResult(int contaId, double precoUnitario, double total) {}

    public CompraResult comprarCrypto(int idCliente, int idCrypto, double quantidade) throws SQLException {
        if (quantidade <= 0) throw new SQLException("Quantidade deve ser > 0.");

        final String sqlContaPorCliente = """
                SELECT id_conta_interna, saldo
                FROM conta_interna
                WHERE cliente_id_cliente = ?
                FOR UPDATE
                """;

        final String sqlCarteiraPorConta = """
                SELECT id_carteira
                FROM carteira
                WHERE conta_interna_id = ?
                """;

        final String sqlDebitaSaldo = """
                UPDATE conta_interna
                SET saldo = saldo - ?
                WHERE id_conta_interna = ?
                """;

        final String sqlMergePosse = """
                MERGE INTO posse p
                USING (SELECT ? AS carteira_id_carteira, ? AS criptomoeda_id_criptomoeda FROM dual) src
                ON (p.carteira_id_carteira = src.carteira_id_carteira AND p.criptomoeda_id_criptomoeda = src.criptomoeda_id_criptomoeda)
                WHEN MATCHED THEN
                    UPDATE SET p.quantidade_criptomoeda = p.quantidade_criptomoeda + ?
                WHEN NOT MATCHED THEN
                    INSERT (carteira_id_carteira, criptomoeda_id_criptomoeda, quantidade_criptomoeda)
                    VALUES (src.carteira_id_carteira, src.criptomoeda_id_criptomoeda, ?)
                """;

        try (Connection conexao = ConnectionFactory.getConnection()) {
            int idConta;
            double saldoAtual;

            try (PreparedStatement stmt = conexao.prepareStatement(sqlContaPorCliente)) {
                stmt.setInt(1, idCliente);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Conta Interna não encontrada para o cliente id=" + idCliente);
                    idConta = rs.getInt("id_conta_interna");
                    saldoAtual = rs.getDouble("saldo");
                }
            }

            Integer idCarteira = null;
            try (PreparedStatement stmt = conexao.prepareStatement(sqlCarteiraPorConta)) {
                stmt.setInt(1, idConta);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) idCarteira = rs.getInt("id_carteira");
                }
            }
            if (idCarteira == null) throw new SQLException("Carteira não encontrada para a conta id=" + idConta);

            double precoUnitario = CryptoPriceSimulator.nextPrice(idCrypto);

            double total = quantidade * precoUnitario;
            if (saldoAtual < total) throw new SQLException("Saldo insuficiente. Saldo=" + saldoAtual + ", total=" + total);

            try (PreparedStatement stmt = conexao.prepareStatement(sqlDebitaSaldo)) {
                stmt.setDouble(1, total);
                stmt.setInt(2, idConta);
                if (stmt.executeUpdate() != 1) throw new SQLException("Falha ao debitar saldo da conta id=" + idConta);
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sqlMergePosse)) {
                stmt.setInt(1, idCarteira);
                stmt.setInt(2, idCrypto);
                stmt.setDouble(3, quantidade);
                stmt.setDouble(4, quantidade);
                stmt.executeUpdate();
            }

            return new CompraResult(idConta, precoUnitario, total);

        } catch (SQLException e) {
            throw e;
        }
    }

    public record VendaResult(int contaId, double precoUnitario, double totalCreditado) {}

    public VendaResult venderCrypto(int idCliente, int idCrypto, double quantidade) throws SQLException {
        if (quantidade <= 0) throw new SQLException("Quantidade deve ser > 0.");

        final String sqlContaPorCliente = """
                SELECT id_conta_interna, saldo
                FROM conta_interna
                WHERE cliente_id_cliente = ?
                FOR UPDATE
                """;

        final String sqlCarteiraPorConta = """
                SELECT id_carteira
                FROM carteira
                WHERE conta_interna_id = ?
                """;

        final String sqlQtdPosse = """
                SELECT quantidade_criptomoeda
                FROM posse
                WHERE carteira_id_carteira = ? AND criptomoeda_id_criptomoeda = ?
                FOR UPDATE
                """;

        final String sqlAtualizaPosse = """
                UPDATE posse
                SET quantidade_criptomoeda = quantidade_criptomoeda - ?
                WHERE carteira_id_carteira = ? AND criptomoeda_id_criptomoeda = ? AND quantidade_criptomoeda >= ?
                """;

        final String sqlDeleteSeZero = """
                DELETE FROM posse
                WHERE carteira_id_carteira = ? AND criptomoeda_id_criptomoeda = ? AND quantidade_criptomoeda = 0
                """;

        final String sqlCreditaSaldo = """
            UPDATE conta_interna
            SET saldo = saldo + ?
            WHERE id_conta_interna = ?
            """;

        try (Connection conexao = ConnectionFactory.getConnection()) {
            int idConta;
            try (PreparedStatement stmt = conexao.prepareStatement(sqlContaPorCliente)) {
                stmt.setInt(1, idCliente);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Conta não encontrada para o cliente id=" + idCliente);
                    idConta = rs.getInt("id_conta_interna");
                }
            }

            Integer idCarteira = null;
            try (PreparedStatement stmt = conexao.prepareStatement(sqlCarteiraPorConta)) {
                stmt.setInt(1, idConta);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) idCarteira = rs.getInt("id_carteira");
                }
            }
            if (idCarteira == null) throw new SQLException("Carteira não encontrada para a conta id=" + idConta);

            double qtdAtual = 0.0;
            try (PreparedStatement stmt = conexao.prepareStatement(sqlQtdPosse)) {
                stmt.setInt(1, idCarteira);
                stmt.setInt(2, idCrypto);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) qtdAtual = rs.getDouble("quantidade_criptomoeda");
                }
            }
            if (qtdAtual < quantidade) {
                throw new SQLException("Quantidade insuficiente em posse. Atual=" + qtdAtual + ", desejada=" + quantidade);
            }

            double precoUnitario = CryptoPriceSimulator.nextPrice(idCrypto);
            double totalCreditado = quantidade * precoUnitario;

            try (PreparedStatement stmt = conexao.prepareStatement(sqlAtualizaPosse)) {
                stmt.setDouble(1, quantidade);
                stmt.setInt(2, idCarteira);
                stmt.setInt(3, idCrypto);
                stmt.setDouble(4, quantidade);
                if (stmt.executeUpdate() != 1) {
                    throw new SQLException("Falha ao subtrair quantidade na posse");
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sqlDeleteSeZero)) {
                stmt.setInt(1, idCarteira);
                stmt.setInt(2, idCrypto);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sqlCreditaSaldo)) {
                stmt.setDouble(1, totalCreditado);
                stmt.setInt(2, idConta);
                if (stmt.executeUpdate() != 1) {
                    throw new SQLException("Falha ao creditar saldo na conta id= " + idConta);
                }
            }
            return new VendaResult(idConta, precoUnitario, totalCreditado);
        } catch (SQLException e) {
            throw e;
        }
    }




}


