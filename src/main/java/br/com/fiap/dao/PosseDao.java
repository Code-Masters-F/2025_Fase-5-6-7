package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Carteira;
import br.com.fiap.model.Crypto;
import br.com.fiap.model.PosseClienteCrypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PosseDao {
    Connection conexao;

    public PosseDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public List<PosseClienteCrypto> listarPossesPorCarteira(int carteiraId) throws SQLException {
        String sql = """
                SELECT carteira_id_carteira, crypto_id_crypto, quantidade_crypto
                FROM posse
                WHERE carteira_id_carteira = ?
                ORDER BY crypto_id_crypto
                """;

        List<PosseClienteCrypto> posses = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, carteiraId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Carteira carteira = new Carteira();
                    carteira.setId(rs.getInt("carteira_id_carteira"));

                    Crypto crypto = new Crypto();
                    crypto.setId(rs.getInt("crypto_id_crypto"));

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
