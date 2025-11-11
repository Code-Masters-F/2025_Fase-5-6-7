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

    public void inserirCriptomoeda(Criptomoeda criptomoeda) throws SQLException {
        String sql = "INSERT INTO criptomoeda (nome, sigla, data_lancamento) VALUES (?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, criptomoeda.getNome().trim());
            stmt.setString(2, criptomoeda.getSigla().trim().toUpperCase());
            stmt.setDate(3, java.sql.Date.valueOf(criptomoeda.getDataLancamento()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException ("Falha ao inserir Criptomoeda (nome=" + criptomoeda.getNome() + ", sigla=" + criptomoeda.getSigla() + ")", e);
        }
    }

    public List<Criptomoeda> listarCriptomoedas() throws SQLException {
        List<Criptomoeda> criptomoedas = new ArrayList<>();

        String sql = "SELECT id_criptomoeda, nome, sigla, data_lancamento FROM criptomoeda";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_criptomoeda");
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

    public Criptomoeda consultarCriptomoeda(int id) throws SQLException {
        String sql = "SELECT id_criptomoeda, nome, sigla, data_lancamento FROM criptomoeda WHERE id_criptomoeda = ?";

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

    public void atualizarCriptomoeda(int id, String novoNome, String novaSigla) throws SQLException {
        String sql = "UPDATE criptomoeda SET nome = ?, sigla = ? WHERE id_criptomoeda = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoNome.trim());
            stmt.setString(2, novaSigla.trim().toUpperCase());
            stmt.setInt(3, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Falha ao atualizar Criptomoeda (id=" + id + ")", e);
        }
    }

    public void deletarCriptomoeda(int id) throws SQLException {
        Connection conexao = null;
        try {
            conexao = ConnectionFactory.getConnection();
            conexao.setAutoCommit(false);

            String sqlPosse = "DELETE FROM posse WHERE criptomoeda_id_criptomoeda = ?";
            try (PreparedStatement stmtPosse = conexao.prepareStatement(sqlPosse)) {
                stmtPosse.setInt(1, id);
                stmtPosse.executeUpdate();
            }

            String sqlTransacao = "DELETE FROM transacao_criptomoeda WHERE criptomoeda_id_criptomoeda = ?";
            try (PreparedStatement stmtTransacao = conexao.prepareStatement(sqlTransacao)) {
                stmtTransacao.setInt(1, id);
                stmtTransacao.executeUpdate();
            }

            String sqlCrypto = "DELETE FROM criptomoeda WHERE id_criptomoeda = ?";
            try (PreparedStatement stmtCrypto = conexao.prepareStatement(sqlCrypto)) {
                stmtCrypto.setInt(1, id);
                stmtCrypto.executeUpdate();
            }

            conexao.commit();
        } catch (SQLException e) {
            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Erro ao fazer rollback: " + ex.getMessage(), ex);
                }
            }
            throw new SQLException("Falha ao deletar Criptomoeda (id=" + id + ")", e);
        } finally {
            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException e) {

                }
            }
        }
    }
}
