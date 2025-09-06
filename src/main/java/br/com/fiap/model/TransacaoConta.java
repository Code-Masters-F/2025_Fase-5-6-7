package br.com.fiap.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransacaoConta {
    private static int proximoId = 1;
    private int id;
    private double valor;
    private LocalDateTime dataHora;
    private int numeroContaOrigem;
    private int numeroContaDestino;
    private int agenciaOrigem;
    private int agenciaDestino;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Contrutor para registrar transferencias entre contas genéricamente
     * @param valor valor para transferencia
     * @param numeroContaOrigem conta que está saindo o dinheiro
     * @param numeroContaDestino conta que está chegando o dinheiro
     * @param agenciaOrigem agencia que está saindo o dinheiro
     * @param agenciaDestino agencia que está chegando o dinheiro
     */
    public TransacaoConta(double valor, int numeroContaOrigem,
                          int numeroContaDestino, int agenciaOrigem, int agenciaDestino) {
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
        this.numeroContaOrigem = numeroContaOrigem;
        this.numeroContaDestino = numeroContaDestino;
        this.agenciaOrigem = agenciaOrigem;
        this.agenciaDestino = agenciaDestino;
        this.id = proximoId++;

        // registra na main a transação
        //Main.todasTransacoesConta.put(this.id, this);
    }
    
    public int getId() {
        return id;
    }
    
    public double getValor() {
        return valor;
    }
    
    public String getDataHora() {
        return dataHora.format(formatter);
    }
    
    public int getNumeroContaOrigem() {
        return numeroContaOrigem;
    }
    
    public int getNumeroContaDestino() {
        return numeroContaDestino;
    }
    
    public int getAgenciaOrigem() {
        return agenciaOrigem;
    }
    
    public int getAgenciaDestino() {
        return agenciaDestino;
    }
    
    @Override
    public String toString() {
        return "TransacaoConta{" +
                "id=" + id +
                ", valor=" + valor +
                ", data=" + getDataHora() +
                ", numeroContaOrigem='" + numeroContaOrigem + '\'' +
                ", numeroContaDestino='" + numeroContaDestino + '\'' +
                ", agenciaOrigem='" + agenciaOrigem + '\'' +
                ", agenciaDestino='" + agenciaDestino + '\'' +
                '}';
    }
}
