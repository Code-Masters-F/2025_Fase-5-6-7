package br.com.fiap.model;

import java.math.BigDecimal;
import java.time.Instant;

public record HistoricoTransacao(
        long idTransacao,
        Instant dataHora,
        TipoTransacaoFiat TipoTransacaoFiat,     // DEBITO ou CREDITO
        BigDecimal valor,                // negativo p/ debito (perspectiva da conta consultada)
        long idContaContraparte,
        String nomeContraparte
) {}
