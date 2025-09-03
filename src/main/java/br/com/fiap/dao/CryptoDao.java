package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CryptoDao {
    private Connection conexao;

    public CryptoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserirCrypto(Crypto crypto) throws SQLException {
        String sql = "INSERT INTO crypto (nome, sigla, data_lancamento) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, crypto.getNome());
            stmt.setString(2, crypto.getSigla());
            stmt.setDate(3, java.sql.Date.valueOf(crypto.getDataLancamento()));

            stmt.executeUpdate();
        }
    }

    public List<Crypto> listarCryptos() throws SQLException {
        List<Crypto> cryptos = new ArrayList<>();

        String sql = "SELECT id_crypto, nome, sigla, data_lancamento FROM crypto";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_crypto");
                String nome = rs.getString("nome");
                String sigla = rs.getString("sigla");
                LocalDate dataLancamento = rs.getDate("data_lancamento").toLocalDate();

                Crypto crypto = new Crypto(nome, sigla, dataLancamento);
                crypto.setId(id);

                cryptos.add(crypto);
            }
        }
        return cryptos;
    }

    public Crypto pesquisar(int id) throws SQLException {
        String sql = "SELECT * FROM crypto WHERE cd_produto = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setInt(1, id);

            try (ResultSet result = stm.executeQuery()) {
                String nome = result.getString("nome");
                String sigla = result.getString("sigla");
                LocalDate dataLancamento = result.getDate("data_lancamento").toLocalDate();
                return new Crypto(nome, sigla, id, dataLancamento);
            }

        }
    }


}
