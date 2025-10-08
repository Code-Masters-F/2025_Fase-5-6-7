package br.com.fiap.model;

import java.time.LocalDateTime;

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

    public int getNumeroContaInterna() {
        return contaInterna.getNumeroConta();
    }

    public int getNumeroContaExterna() {
        return contaExterna.getNumeroConta();
    }

    public void setNumeroContaInterna(int novoNumero) {
        this.contaInterna.setNumeroConta(novoNumero);
    }

    public void setNumeroContaExterna(int novoNumero) {
        this.contaExterna.setNumeroConta(novoNumero);
    }

    public int getAgenciaContaInterna() {
        return contaInterna.getNumeroAgencia();
    }

    public int getAgenciaContaExterna() {
        return contaExterna.getNumeroAgencia();
    }

    public void setAgenciaContaInterna(int novaAgencia) {
        this.contaInterna.setNumeroAgencia(novaAgencia);
    }

    public void setAgenciaContaExterna(int novaAgencia) {
        this.contaExterna.setNumeroAgencia(novaAgencia);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double novoValor) {
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
