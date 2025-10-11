package br.com.fiap.service;

import br.com.fiap.dao.ContaInternaDao;
import br.com.fiap.dao.TransacaoContaDao;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.ContaInterna;
import br.com.fiap.model.TipoTransacaoFiat;
import br.com.fiap.model.TransacaoConta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class TransacaoContaService {

    private final TransacaoContaDao transacaoDao = new TransacaoContaDao();
    private final ContaInternaDao contaInternaDao = new ContaInternaDao();

    public void registrarTransacao(TransacaoConta transacao) throws SQLException {
        try (Connection cx = ConnectionFactory.getConnection()) {
            cx.setAutoCommit(false);

            if (transacao.getTipo() == TipoTransacaoFiat.SAQUE) {
                contaInternaDao.debitarSaldo(cx, transacao.getContaInterna().getId(), transacao.getValor());
            }

            if (transacao.getTipo() == TipoTransacaoFiat.DEPOSITO) {
                contaInternaDao.adicionarSaldo(cx, transacao.getContaInterna().getId(), transacao.getValor());
            }

            transacaoDao.inserirTransacaoConta(cx, transacao);
            cx.commit();
        } catch (SQLException e) {
            throw e;
        }
    }
}
