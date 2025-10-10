package br.com.fiap.model;

public class ContaExterna {

    private int id;
    private Cliente cliente;
    private String numeroConta;
    private String agencia;
    private int codigoBancoExterno;
    private String nomeBanco;

    public ContaExterna(Cliente cliente, String numeroConta, String agencia, int codigoBancoExterno, String nomeBanco) {
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

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setNumeroAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setCodigoBancoExterno(int codigoBancoExterno) {
        this.codigoBancoExterno = codigoBancoExterno;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public String getNumeroAgencia() {
        return agencia;
    }

    public int getCodigoBancoExterno() {
        return codigoBancoExterno;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }


}
