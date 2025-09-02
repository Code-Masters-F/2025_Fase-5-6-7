package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClienteDao {
    private Connection conexao;

    public ClienteDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserirCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, email, cpf, data_nascimento) VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getEmail());
        stmt.setString(3, cliente.getCpf());
        stmt.setDate(4, java.sql.Date.valueOf(cliente.getDataNascimento()));

        stmt.executeUpdate();
        fecharConexao();
    }

    public Cliente consultarClientePorId(int idCliente) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCPF(rs.getString("cpf"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
            }

            fecharConexao();
            return cliente;
    }


    private void fecharConexao() throws SQLException {
        conexao.close();
    }

}
