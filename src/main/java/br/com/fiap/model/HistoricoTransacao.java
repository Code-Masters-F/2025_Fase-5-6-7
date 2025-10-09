package br.com.fiap.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Representa uma transação no histórico de uma conta.
 *
 * @param idTransacao ID da transação FIAT
 * @param dataHora Data e hora da transação
 * @param tipo Tipo da transação (SAQUE ou DEPOSITO)
 * @param valor Valor da transação
 * @param idContaContraparte ID da conta da contraparte
 * @param nomeContraparte Nome do cliente da contraparte
 */
public record HistoricoTransacao(
        long idTransacao,
        Instant dataHora,
        TipoTransacaoFiat tipo,
        BigDecimal valor,
        long idContaContraparte,
        String nomeContraparte
) {
}