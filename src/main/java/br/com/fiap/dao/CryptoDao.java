package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CryptoDao {
    private Connection conexao;

    public CryptoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserirCrypto(Crypto crypto) throws SQLException {
        String sql = "INSERT INTO crypto (nome, sigla, data_lancamento) VALUES (?, ?, ?)";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, crypto.getNome());
        stmt.setString(2, crypto.getSigla());
        stmt.setDate(3, java.sql.Date.valueOf(crypto.getDataLancamento()));

        stmt.executeUpdate();
        fecharConexao();
    }

    public void exibirCryptos() throws SQLException {
        String sql = "SELECT * FROM crypto";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            while (rs.next()) {

                int id = rs.getInt("id_crypto");
                String nome = rs.getString("nome");
                String sigla = rs.getString("sigla");
                LocalDate data_lancamento = rs.getDate("data_lancamento").toLocalDate();

                System.out.println(
                        "ID: " + id +
                                ", Nome: " + nome +
                                ", Sigla: " + sigla +
                                ", Data de Lançamento: " + data_lancamento
                );

            }
        } else {
            System.out.println("Nenhuma Crypto foi encontrada!");
        }
    }


    private void fecharConexao() throws SQLException {
        conexao.close();
    }




}
