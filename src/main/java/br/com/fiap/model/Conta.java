package br.com.fiap.model;

import java.time.LocalDate;

public abstract class Conta {
    protected int id;
    protected int numeroConta;
    protected int agencia;
    private LocalDate dataAbertura;

    public Conta(int numeroConta, int agencia, int id) {
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.dataAbertura = LocalDate.now();
        this.id = id;
    }

    public Conta() {

    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public int getAgencia() {
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

