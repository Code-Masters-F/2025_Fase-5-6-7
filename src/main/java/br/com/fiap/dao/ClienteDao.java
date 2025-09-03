package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Cliente;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.sql.*;

public class ClienteDao {
    private Connection conexao;

    public ClienteDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public int inserirCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, email, cpf, data_nascimento) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, new String[] {"id_cliente"})) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCpf());
            stmt.setDate(4, java.sql.Date.valueOf(cliente.getDataNascimento()));

            int rows = stmt.executeUpdate();
            if (rows != 1) throw new SQLException("Falha ao inserir cliente");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {

                    Object key = rs.getObject(1);
                    if (key instanceof BigDecimal bd) {
                        return bd.intValue();
                    }
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Não foi possível obter o id gerado do cliente.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Cliente consultarClientePorId(int idCliente) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCPF(rs.getString("cpf"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                return cliente;
            }
        }
        return null;
    }

    public boolean existeClientePorCpfOuEmail(String cpf, String email) throws SQLException {
        String sql = "SELECT 1 FROM CLIENTE WHERE cpf = ? OR LOWER(email) = LOWER(?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }


}
