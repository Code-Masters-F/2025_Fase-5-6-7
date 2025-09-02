package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Carteira;

import java.sql.Connection;
import java.sql.SQLException;

public class CarteiraDao {
    Connection conexao;

    public CarteiraDao() throws SQLException  {
        conexao = ConnectionFactory.getConnection();
    }

    public Carteira exibirCarteira(ContaCliente conta) {

    }





}
