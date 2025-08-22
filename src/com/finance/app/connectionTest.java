package com.finance.app.;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class App
{
    public static void main( String[] args )
    {
        try {
            Connection conexao = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "usuario","senha");
            System.out.println("Conexão realizada!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}