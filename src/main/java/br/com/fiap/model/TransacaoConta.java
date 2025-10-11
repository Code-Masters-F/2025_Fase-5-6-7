package br.com.fiap.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoConta {
    private int id;
    private BigDecimal valor;
    private TipoTransacaoFiat tipoTransacao;
    private LocalDateTime dataHora;
    private ContaInterna contaInterna;
    private ContaExterna contaExterna;

    /**
     * Contrutor para registrar transferencias entre contas genéricamente
     * @param valor valor para transferencia
     */
    public TransacaoConta(ContaExterna contaExterna, ContaInterna contaInterna, BigDecimal valor, TipoTransacaoFiat tipo, LocalDateTime dataHora) {
        this.contaExterna = contaExterna;
        this.contaInterna = contaInterna;
        this.valor = valor;
        this.tipoTransacao = tipo;
        this.dataHora = dataHora;
    }

    public TransacaoConta() {}
    
    public int getId() {
        return id;
    }

    public TipoTransacaoFiat getTipo() {
        return tipoTransacao;
    }

    public void setTipo(TipoTransacaoFiat novoTipo) {
        this.tipoTransacao = novoTipo;
    }

    public ContaExterna getContaExterna() {
        return contaExterna;
    }

    public void setContaExterna(ContaExterna contaExterna) {
        this.contaExterna = contaExterna;
    }

    public ContaInterna getContaInterna() {
        return contaInterna;
    }

    public void setContaInterna(ContaInterna contaInterna) {
        this.contaInterna = contaInterna;
    }

    public String getNumeroContaInterna() {
        return contaInterna.getNumeroConta();
    }

    public String getNumeroContaExterna() {
        return contaExterna.getNumeroConta();
    }

    public void setNumeroContaInterna(String novoNumero) {
        this.contaInterna.setNumeroConta(novoNumero);
    }

    public void setNumeroContaExterna(String novoNumero) {
        this.contaExterna.setNumeroConta(novoNumero);
    }

    public String getAgenciaContaInterna() {
        return contaInterna.getNumeroAgencia();
    }

    public String getAgenciaContaExterna() {
        return contaExterna.getNumeroAgencia();
    }

    public void setAgenciaContaInterna(String novaAgencia) {
        this.contaInterna.setNumeroAgencia(novaAgencia);
    }

    public void setAgenciaContaExterna(String novaAgencia) {
        this.contaExterna.setNumeroAgencia(novaAgencia);
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal novoValor) {
        this.valor = novoValor;
    }
    
    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    public void setDataHora(LocalDateTime novaDataHora) {
        this.dataHora = novaDataHora;

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
