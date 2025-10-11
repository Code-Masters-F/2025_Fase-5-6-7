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
    private ContaInterna contaInterna;
    private StatusOperacao status;
    private Criptomoeda criptomoeda;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // apagar proximoId quando houver conexao com banco de dados
    private static int proximoId = 1;

    public TransacaoCrypto(ContaInterna contaInterna, double quantidadeCrypto, double valorUnitarioCrypto, Criptomoeda criptomoeda,
                           String tipoOperacao) {

        this.contaInterna = contaInterna;
        this.quantidadeCrypto = quantidadeCrypto;
        this.valorUnitarioCrypto = valorUnitarioCrypto;
        this.criptomoeda = criptomoeda;
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

    public ContaInterna getContaCliente() {
        return contaInterna;
    }

    public void setContaCliente(ContaInterna novaContaInterna) {
        this.contaInterna = novaContaInterna;
    }

    public StatusOperacao getStatus() {
        return status;
    }

    public Criptomoeda getCrypto() {
        return criptomoeda;
    }

    public void setCrypto(Criptomoeda novaCriptomoeda) {
        this.criptomoeda = novaCriptomoeda;
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
                ", conta=" + contaInterna +
                ", crypto=" + criptomoeda +
                ", status=" + status +
                '}';
    }
}

