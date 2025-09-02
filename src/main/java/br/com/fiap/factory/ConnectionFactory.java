package br.com.fiap.factory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Vai ser usado sempre para fazer conexão, para não ficar
// repetindo usuario e senha no codigo inteiro
/**
 * Essa classe retorna um objeto Connection. Depois de usa-la precisa
 * fechar a instancia de connection, com o .close().
 */
public class ConnectionFactory {
    public static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    public static final String USUARIO = "RM557577";
    public static final String SENHA = "140206";

    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
