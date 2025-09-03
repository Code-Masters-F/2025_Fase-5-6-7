package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Carteira;
import br.com.fiap.model.PosseClienteCrypto;
import br.com.fiap.utils.CryptoPriceSimulator;

import javax.naming.directory.InitialDirContext;
import java.io.InvalidClassException;
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

//    public Integer buscarCarteiraPorContaId(int idConta) throws SQLException {
//        final String sql = """
//                SELECT carteira_id_carteira
//                FROM conta
//                WHERE id_conta = ?
//                """;
//
//        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
//            stmt.setInt(1, idConta);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return rs.next() ? rs.getInt("carteira_id_carteira") : null;
//            }
//        }
//    }

    public record CompraResult(int contaId, double precoUnitario, double total) {}

    public CompraResult comprarCrypto(int idCliente, int idCrypto, double quantidade) throws SQLException {
        if (quantidade <= 0) throw new SQLException("Quantidade deve ser > 0.");

        final String sqlContaPorCliente = """
                SELECT id_conta, saldo
                FROM conta
                WHERE cliente_id_cliente = ?
                FOR UPDATE
                """;

        final String sqlCarteiraPorConta = """
                SELECT id_carteira
                FROM carteira
                WHERE conta_id_conta = ?
                """;

        final String sqlDebitaSaldo = """
                UPDATE conta
                SET saldo = saldo - ?
                WHERE id_conta = ?
                """;

        final String sqlMergePosse = """
                MERGE INTO posse p
                USING (SELECT ? AS carteira_id_carteira, ? AS crypto_id_crypto FROM dual) src
                ON (p.carteira_id_carteira = src.carteira_id_carteira AND p.crypto_id_crypto = src.crypto_id_crypto)
                WHEN MATCHED THEN
                    UPDATE SET p.quantidade_crypto = p.quantidade_crypto + ?
                WHEN NOT MATCHED THEN
                    INSERT (carteira_id_carteira, crypto_id_crypto, quantidade_crypto)
                    VALUES (src.carteira_id_carteira, src.crypto_id_crypto, ?)
                """;

        try {
            int idConta;
            double saldoAtual;

            try (PreparedStatement stmt = conexao.prepareStatement(sqlContaPorCliente)) {
                stmt.setInt(1, idCliente);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Conta não encontrada para o cliente id=" + idCliente);
                    idConta = rs.getInt("id_conta");
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
            conexao.rollback();
            throw e;
        }
    }

    public record VendaResult(int contaId, double precoUnitario, double totalCreditado) {}

    public VendaResult venderCrypto(int idCliente, int idCrypto, double quantidade) throws SQLException {
        if (quantidade <= 0) throw new SQLException("Quantidade deve ser > 0.");

        final String sqlContaPorCliente = """
                SELECT id_conta, saldo
                FROM conta
                WHERE cliente_id_cliente = ?
                FOR UPDATE
                """;

        final String sqlCarteiraPorConta = """
                SELECT id_carteira
                FROM carteira
                WHERE conta_id_conta = ?
                """;

        final String sqlQtdPosse = """
                SELECT quantidade_crypto
                FROM posse
                WHERE carteira_id_carteira = ? AND crypto_id_crypto = ?
                FOR UPDATE
                """;

        final String sqlAtualizaPosse = """
                UPDATE posse
                SET quantidade_crypto = quantidade_crypto - ?
                WHERE carteira_id_carteira = ? AND crypto_id_crypto = ? AND quantidade_crypto >= ?
                """;

        final String sqlDeleteSeZero = """
                DELETE FROM posse
                WHERE carteira_id_carteira = ? AND crypto_id_crypto = ? AND quantidade_crypto = 0
                """;

        final String sqlCreditaSaldo = """
            UPDATE conta
            SET saldo = saldo + ?
            WHERE id_conta = ?
            """;

        try {
            int idConta;
            try (PreparedStatement stmt = conexao.prepareStatement(sqlContaPorCliente)) {
                stmt.setInt(1, idCliente);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Conta não encontrada para o cliente id=" + idCliente);
                    idConta = rs.getInt("id_conta");
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
                    if (rs.next()) qtdAtual = rs.getDouble("quantidade_crypto");
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
            conexao.rollback();
            throw e;
        }
    }




}


