package br.com.fiap.model;

public class ContaExterna {

    private int id;
    private Cliente cliente;
    private int numeroConta;
    private int agencia;
    private int codigoBancoExterno;
    private String nomeBanco;

    public ContaExterna(Cliente cliente, int numeroConta, int agencia, int codigoBancoExterno, String nomeBanco) {
        this.cliente = cliente;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.codigoBancoExterno = codigoBancoExterno;
        this.nomeBanco = nomeBanco;
    }

    public ContaExterna() {

    };

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setNumeroAgencia(int agencia) {
        this.agencia = agencia;
    }

    public void setCodigoBancoExterno(int codigoBancoExterno) {
        this.codigoBancoExterno = codigoBancoExterno;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public int getNumeroAgencia() {
        return agencia;
    }

    public int getCodigoBancoExterno() {
        return codigoBancoExterno;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }


}
