package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Criptomoeda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class CryptoDao {

    public CryptoDao() {
    }

    public void inserirCrypto(Criptomoeda criptomoeda) throws SQLException {
        String sql = "INSERT INTO crypto (nome, sigla, data_lancamento) VALUES (?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, criptomoeda.getNome().trim());
            stmt.setString(2, criptomoeda.getSigla().trim().toUpperCase());
            stmt.setDate(3, java.sql.Date.valueOf(criptomoeda.getDataLancamento()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException ("Falha ao inserir CRYPTO (nome=" + criptomoeda.getNome() + ", sigla=" + criptomoeda.getSigla() + ")", e);
        }
    }

    public List<Criptomoeda> listarCryptos() throws SQLException {
        List<Criptomoeda> criptomoedas = new ArrayList<>();

        String sql = "SELECT id_crypto, nome, sigla, data_lancamento FROM crypto";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_crypto");
                String nome = rs.getString("nome").trim();
                String sigla = rs.getString("sigla").trim();

                Date data = rs.getDate("data_lancamento");
                LocalDate dataLancamento = (data != null) ? data.toLocalDate() : null;

                Criptomoeda criptomoeda = new Criptomoeda(nome, sigla, dataLancamento);
                criptomoeda.setId(id);

                criptomoedas.add(criptomoeda);
            }
        }
        return criptomoedas;
    }

    public Criptomoeda consultarCrypto(int id) throws SQLException {
        String sql = "SELECT id_crypto, nome, sigla, data_lancamento FROM crypto WHERE id_crypto = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setInt(1, id);

            try (ResultSet result = stm.executeQuery()) {
                if (result.next())  {
                    String nome = result.getString("nome");
                    String sigla = result.getString("sigla");
                    LocalDate dataLancamento = result.getDate("data_lancamento").toLocalDate();
                    return new Criptomoeda(nome, sigla, id, dataLancamento);
                }
            }
            return null;
        }
    }
}
