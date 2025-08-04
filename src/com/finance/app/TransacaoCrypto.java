package com.finance.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TransacaoCrypto {
    private int id;
    private String tipoOperacao; // Tipo da operação (ex: "compra", "venda")
    private double valorTotal;
    private double quantidadeCrypto;
    private LocalDateTime dataHora;
    private double valorUnitarioCrypto;
    private ContaCliente contaCliente;
    private boolean status;
    private Crypto crypto;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // apagar proximoId quando houver conexao com banco de dados
    private static int proximoId = 1;

    public TransacaoCrypto(ContaCliente contaCliente, double quantidadeCrypto, double valorUnitarioCrypto, Crypto crypto,
                           String tipoOperacao) {

        this.contaCliente = contaCliente;
        this.quantidadeCrypto = quantidadeCrypto;
        this.valorUnitarioCrypto = valorUnitarioCrypto;
        this.crypto = crypto;
        this.tipoOperacao = tipoOperacao;
        this.dataHora = LocalDateTime.now(); // Define a data atual
        this.valorTotal = quantidadeCrypto * valorUnitarioCrypto; // Calcula o valor total comprado/vendido
        this.status = true;
        this.id = proximoId++;

        // Registra na main a transação
        Main.todasTransacoesCrypto.put(this.id, this);
    }

    // Getters

    public int getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getQuantidadeCrypto() {
        return quantidadeCrypto;
    }

    public String getDataHora() {
        return dataHora.format(formatter);
    }

    public double getValorUnitarioCrypto() {
        return valorUnitarioCrypto;
    }

    public Conta getContaCliente() {
        return contaCliente;
    }

    public boolean getStatus() {
        return status;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }


    /**
     * Altera o status da transação (ativa ou cancelada).
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransacaoCrypto {" +
                "id=" + id +
                ", tipoOperacao='" + tipoOperacao + '\'' +
                ", valorTotal=" + valorTotal +
                ", quantidadeCrypto=" + quantidadeCrypto +
                ", valorUnitarioCrypto=" + valorUnitarioCrypto +
                ", data=" + getDataHora() +
                ", conta=" + contaCliente +
                ", crypto=" + crypto +
                ", status=" + status +
                '}';
    }
}

