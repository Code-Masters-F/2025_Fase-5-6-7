package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Cliente;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ClienteDao {

    public ClienteDao() {
    }

    public int inserirCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, email, cpf, data_nascimento) VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[] {"id_cliente"})) {

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

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("NOME");
                String email = rs.getString("EMAIL");
                String cpf = rs.getString("CPF");
                LocalDate dataNascimento = rs.getDate("DATA_NASCIMENTO").toLocalDate();

                cliente = new Cliente(cpf, nome, email, dataNascimento, idCliente);
                return cliente;
            }
        }
        return null;
    }

    public boolean existeClientePorCpfOuEmail(String cpf, String email) throws SQLException {
        String sql = "SELECT 1 FROM CLIENTE WHERE cpf = ? OR LOWER(email) = LOWER(?)";
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void atualizarClientePorId (int idCliente, String nome, String email) throws SQLException{
        String sql = "UPDATE cliente SET nome = ?, email = ? WHERE id_cliente = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setInt(3, idCliente);

            int rows = stmt.executeUpdate();
            if (rows != 1) throw new SQLException("Falha ao atualizar o cliente");

        }
    }

    public void deletarClientePorId (int idCliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            int rows = stmt.executeUpdate();
            if (rows != 1) throw new SQLException("Falha ao deletar cliente");
        }
    }

    public Map<Cliente, Integer> listarContaInternaClientesCadastrados() throws SQLException {
        final String SQL = """
                SELECT c.*, ci.id_conta_interna
                FROM cliente c
                INNER JOIN conta_interna ci
                    ON c.id_cliente = ci.cliente_id_cliente
                """;

        Map<Cliente, Integer> clientesCadastrados = new HashMap<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(SQL);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                int idCliente = result.getInt("id_cliente");
                String nome = result.getString("nome");
                String email = result.getString("email");
                String cpf = result.getString("cpf");
                LocalDate dataNascimento = result.getDate("data_nascimento").toLocalDate();
                int idConta = result.getInt("id_conta_interna");

                Cliente cliente = new Cliente(cpf, nome, email, dataNascimento, idCliente);
                clientesCadastrados.put(cliente, idConta);
            }
        }
        return clientesCadastrados;
    }

}
