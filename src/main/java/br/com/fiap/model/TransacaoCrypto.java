package br.com.fiap.model;

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
    private StatusOperacao status;
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
        this.status = StatusOperacao.PENDENTE;

        // Registra na main a transação
        //Main.todasTransacoesCrypto.put(this.id, this);
    }

    public TransacaoCrypto() {

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

    public void setQuantidadeCrypto(double novaQuantidadeCrypto) {
        this.quantidadeCrypto = novaQuantidadeCrypto;
    }

    public String getDataHora() {
        return dataHora.format(formatter);
    }

    public double getValorUnitarioCrypto() {
        return valorUnitarioCrypto;
    }

    public void setValorUnitarioCrypto(double novoValorUnitario) {
        this.valorUnitarioCrypto = novoValorUnitario;
    }

    public ContaCliente getContaCliente() {
        return contaCliente;
    }

    public void setContaCliente(ContaCliente novaContaCliente) {
        this.contaCliente = novaContaCliente;
    }

    public StatusOperacao getStatus() {
        return status;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto novaCrypto) {
        this.crypto = novaCrypto;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String novoTipoOperacao) {
        this.tipoOperacao = novoTipoOperacao;
    }


    /**
     * Altera o status da transação (ativa ou cancelada).
     */
    public void setStatus(StatusOperacao status) {
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

