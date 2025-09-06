package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PosseDao {

    public PosseDao() {
    }

    public List<PosseClienteCrypto> listarPossesPorCarteira(int contaId) throws SQLException {
        String sql = """
        SELECT
            car.id_carteira,
            ct.id_conta, ct.numero_conta, ct.agencia, ct.CLIENTE_id_cliente,
            cli.id_cliente, cli.nome AS nome_cliente,
            cr.id_crypto, cr.nome AS nome_crypto, cr.sigla,
            p.quantidade_crypto
        FROM CONTA ct
        JOIN CARTEIRA car      ON car.CONTA_id_conta = ct.id_conta
        JOIN POSSE p           ON p.CARTEIRA_id_carteira = car.id_carteira
        JOIN CRYPTO cr         ON cr.id_crypto = p.CRYPTO_id_crypto
        JOIN CLIENTE cli       ON cli.id_cliente = ct.CLIENTE_id_cliente
        WHERE ct.id_conta = ?
        ORDER BY cr.nome
    """;

        List<PosseClienteCrypto> posses = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, contaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id_cliente"));
                    cliente.setNome(rs.getString("nome_cliente"));

                    ContaCliente conta = new ContaCliente();
                    conta.setId(rs.getInt("id_conta"));
                    conta.setNumeroConta(rs.getInt("numero_conta"));
                    conta.setNumeroAgencia(rs.getInt("agencia"));
                    conta.setCliente(cliente);

                    Carteira carteira = new Carteira();
                    carteira.setId(rs.getInt("id_carteira"));
                    carteira.setContaCliente(conta);

                    Crypto crypto = new Crypto();
                    crypto.setId(rs.getInt("id_crypto"));
                    crypto.setNome(rs.getString("nome_crypto").trim());
                    crypto.setSigla(rs.getString("sigla").trim());

                    PosseClienteCrypto posse = new PosseClienteCrypto();
                    posse.setCarteira(carteira);
                    posse.setCrypto(crypto);
                    posse.setQuantidade(rs.getDouble("quantidade_crypto"));

                    posses.add(posse);
                }
            }
        }
        return posses;
    }
}
