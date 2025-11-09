package br.com.fiap.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


// EXEMPLO de como se usar o ConnectionFactory

public class TestConnection {

    private static Connection conexao;

    public static void main(String[] args) {
        try {
            conexao = ConnectionFactory.getConnection();
            System.out.println("Conexão estabelecida com sucesso!");

            conexao.close();
        } catch (Exception e) {
            System.err.println("Falha ao conectar ao banco de dados. Verifique as credenciais ou a URL.");
        }
    }
}
