package br.com.fiap.factory;

import java.sql.Connection;
import java.sql.DriverManager;


// EXEMPLO de como se usar o ConnectionFactory

public class TestConnection {

    private static Connection conexao;

    public static void main(String[] args) {
        try {

            conexao = ConnectionFactory.getConnection();
            System.out.println("Conexão estabelecida com sucesso!");

            // Precisa fechar quando a classe que está usando a conexão não for usar mais
            // Na fiap o professor coloca um médoto para fechar
            conexao.close();
        } catch (Exception e) {
            System.err.println("Falha ao conectar ao banco de dados. Verifique as credenciais ou a URL.");
        }
    }
}
