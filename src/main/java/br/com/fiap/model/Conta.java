package br.com.fiap.model;

import java.time.LocalDate;

public abstract class Conta {
    protected int id;
    protected String numeroConta;
    protected String agencia;
    private LocalDate dataAbertura;

    public Conta(String numeroConta, String agencia, int id) {
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.dataAbertura = LocalDate.now();
        this.id = id;
    }

    public Conta() {

    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getNumeroAgencia() {
        return agencia;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public int getId() {
        return id;
    }

    public abstract double getSaldo();

    public abstract boolean depositar(float valor);
}

