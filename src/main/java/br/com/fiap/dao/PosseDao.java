package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PosseDao {

    public PosseDao() {
    }

    public List<PosseClienteCrypto> listarPossesPorCarteira(int contaId) throws SQLException {
        String sql = """
        SELECT
            car.id_carteira,
            ct.id_conta_interna, ct.numero_conta, ct.agencia, ct.CLIENTE_id_cliente,
            cli.id_cliente, cli.nome AS nome_cliente,
            cr.id_criptomoeda, cr.nome AS nome_criptomoeda, cr.sigla,
            p.quantidade_criptomoeda
        FROM CONTA_INTERNA ct
        JOIN CARTEIRA car      ON car.conta_interna_id = ct.id_conta_interna
        JOIN POSSE p           ON p.CARTEIRA_id_carteira = car.id_carteira
        JOIN CRIPTOMOEDA cr         ON cr.id_criptomoeda = p.criptomoeda_id_criptomoeda
        JOIN CLIENTE cli       ON cli.id_cliente = ct.CLIENTE_id_cliente
        WHERE ct.id_conta_interna = ?
        ORDER BY cr.nome
    """;
        List<PosseClienteCrypto> posses = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, contaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id_cliente"));
                    cliente.setNome(rs.getString("nome_cliente"));

                    ContaInterna conta = new ContaInterna();
                    conta.setId(rs.getInt("id_conta_interna"));
                    conta.setNumeroConta(rs.getString("numero_conta"));
                    conta.setNumeroAgencia(rs.getString("agencia"));
                    conta.setCliente(cliente);

                    Carteira carteira = new Carteira();
                    carteira.setId(rs.getInt("id_carteira"));
                    carteira.setContaCliente(conta);

                    Criptomoeda criptomoeda = new Criptomoeda();
                    criptomoeda.setId(rs.getInt("id_criptomoeda"));
                    criptomoeda.setNome(rs.getString("nome_criptomoeda").trim());
                    criptomoeda.setSigla(rs.getString("sigla").trim());

                    PosseClienteCrypto posse = new PosseClienteCrypto();
                    posse.setCarteira(carteira);
                    posse.setCrypto(criptomoeda);
                    posse.setQuantidade(rs.getDouble("quantidade_criptomoeda"));

                    posses.add(posse);
                }
            }
        }
        return posses;
    }
}
