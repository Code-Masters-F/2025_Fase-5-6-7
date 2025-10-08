package br.com.fiap.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransacaoConta {
    private int id;
    private double valor;
    private TipoTransacaoFiat tipoTransacao;
    private LocalDateTime dataHora;
    private ContaInterna contaInterna;
    private ContaExterna contaExterna;

    /**
     * Contrutor para registrar transferencias entre contas genéricamente
     * @param valor valor para transferencia
     */
    public TransacaoConta(ContaExterna contaExterna, ContaInterna contaInterna, double valor, TipoTransacaoFiat tipo, LocalDateTime dataHora) {
        this.contaExterna = contaExterna;
        this.contaInterna = contaInterna;
        this.valor = valor;
        this.tipoTransacao = tipo;
        this.dataHora = dataHora;
    }
    
    public int getId() {
        return id;
    }

    public TipoTransacaoFiat getTipo() {
        return tipoTransacao;
    }

    public int getNumeroContaInterna() {
        return contaInterna.getNumeroConta();
    }

    public int getNumeroContaExterna() {
        return contaExterna.getNumeroConta();
    }

    public int getAgenciaContaInterna() {
        return contaInterna.getNumeroAgencia();
    }

    public int getAgenciaContaExterna() {
        return contaExterna.getNumeroAgencia();
    }
    
    public double getValor() {
        return valor;
    }
    
    public LocalDateTime getDataHora() {
        return this.dataHora;
    }
    
    @Override
    public String toString() {
        return "TransacaoConta{" +
                "id=" + id +
                ", valor=" + valor +
                ", data=" + getDataHora() +
                '}';
    }
}
