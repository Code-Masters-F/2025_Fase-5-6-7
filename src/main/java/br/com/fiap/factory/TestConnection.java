package br.com.fiap.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        try {

            String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
            String user = "RM557577";
            String password = "140206";

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");

            conn.close();
        } catch (Exception e) {
            System.out.println("Falha ao conectar ao banco de dados. Verifique as credenciais ou a URL.");
            e.printStackTrace();
        }
    }
}
