package br.com.fiap.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Essa classe retorna um objeto Connection. Depois de usa-la precisa
 * fechar a instancia de connection, com o .close().
 */
public class ConnectionFactory {

    public static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    public static final String USUARIO = "RM557577";
    public static final String SENHA = "140206";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);

        try (Statement st = conn.createStatement()) {
            st.execute("ALTER SESSION SET CURRENT_SCHEMA=" + USUARIO);
        }

        return conn;
    }
}
